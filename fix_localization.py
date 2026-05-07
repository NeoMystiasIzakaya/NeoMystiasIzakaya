#!/usr/bin/env python3
"""
Fix food item English localization, constants, registerItem strings, and texture filenames.
Steps:
1. Read Chinese from NMILanguageProvider.addItemTranslations
2. Look up key in cn_old.json by Chinese value
3. Look up English in en_old.json by key
4. Replace English in addItemTranslations
5. Derive constant name (UPPERCASE, remove quotes, spaces/hyphens -> underscores)
6. Fix registerItem string in NMIFoodItems (lowercase constant)
7. Sync texture filenames
Special cases are logged to fix_log.txt for manual handling.
"""

import json
import re
import os
import sys

BASE = r'c:\Users\Administrator\IdeaProjects\NeoMystiasIzakaya'


def escape_java_str(s):
    """Escape a string for use in Java double quotes."""
    return s.replace('\\', '\\\\').replace('"', '\\"')


def derive_constant(english):
    """Derive Java constant name from English text."""
    # Remove quotes
    clean = english.replace("'", "").replace('"', '')
    # Replace non-alphanumeric (except spaces/hyphens) with space
    clean = re.sub(r"[^a-zA-Z0-9\s\-]", ' ', clean)
    # Spaces and hyphens -> underscores, collapse multiple
    clean = re.sub(r'[\s\-]+', '_', clean.strip())
    clean = re.sub(r'_+', '_', clean)
    return clean.upper()


def main():
    # Load JSON
    with open(os.path.join(BASE, 'code_gen', 'cn_old.json'), 'r', encoding='utf-8') as f:
        cn = json.load(f)
    with open(os.path.join(BASE, 'code_gen', 'en_old.json'), 'r', encoding='utf-8') as f:
        en = json.load(f)

    # Build Chinese value -> key reverse lookup (only item.* entries)
    zh2key = {v: k for k, v in cn.items() if k.startswith('item.mystias_izakaya.')}

    # Read source files
    lang_path = os.path.join(BASE, 'src', 'datagen', 'java', 'icu', 'gensoukyo', 'neo_mystias_izakaya', 'datagen', 'NMILanguageProvider.java')
    food_path = os.path.join(BASE, 'src', 'main', 'java', 'icu', 'gensoukyo', 'neo_mystias_izakaya', 'registry', 'item', 'NMIFoodItems.java')
    tex_dir = os.path.join(BASE, 'src', 'main', 'resources', 'assets', 'neo_mystias_izakaya', 'textures', 'item', 'food')

    with open(lang_path, 'r', encoding='utf-8') as f:
        lang = f.read()
    with open(food_path, 'r', encoding='utf-8') as f:
        food = f.read()

    # Parse food item translations: this.add(NMIFoodItems.CONST, "English", "Chinese");
    pat = re.compile(r'this\.add\(NMIFoodItems\.(\w+),\s*"((?:[^"\\]|\\.)*)",\s*"((?:[^"\\]|\\.)*)"\);')
    log = []
    const_map = {}   # old_const -> new_const
    en_map = {}      # old_const -> new_english
    zh_map = {}      # old_const -> chinese (from addItemTranslations)

    for m in pat.finditer(lang):
        old_c, old_e, zh = m.group(1), m.group(2), m.group(3)
        zh_map[old_c] = zh

        # Step 2: Find key in cn_old by Chinese value
        if zh not in zh2key:
            log.append(f"[NO_CN] {old_c}: Chinese='{zh}' not found in cn_old")
            continue

        key = zh2key[zh]

        # Step 3: Get English from en_old
        if key not in en:
            log.append(f"[NO_EN] {old_c}: key='{key}' not found in en_old")
            continue

        new_e = en[key]
        en_map[old_c] = new_e

        # Step 5: Derive constant name
        new_c = derive_constant(new_e)

        if not new_c or len(new_c) < 2:
            log.append(f"[BAD_CONST] {old_c}: English='{new_e}' -> const='{new_c}'")
            continue

        const_map[old_c] = new_c

    # Get old register strings from NMIFoodItems
    old_regs = {}
    for m in re.finditer(r'public static final DeferredItem<Item>\s+(\w+)\s*=\s*registerItem\(ITEMS,\s*"([^"]+)"', food):
        old_regs[m.group(1)] = m.group(2)

    # Check for constant name collisions
    new_to_old = {}
    for old_c, new_c in const_map.items():
        if new_c in new_to_old and new_to_old[new_c] != old_c:
            log.append(f"[COLLISION] {old_c} and {new_to_old[new_c]} both map to {new_c}")
        new_to_old[new_c] = old_c

    # Check if new constant collides with an existing unchanged constant
    all_food_consts = set(old_regs.keys())
    for old_c, new_c in const_map.items():
        if new_c in all_food_consts and new_c not in const_map:
            log.append(f"[COLLISION_EXISTING] {old_c} -> {new_c} collides with existing constant {new_c}")

    # ============================================================
    # Apply changes
    # ============================================================

    # Step 4: Update NMILanguageProvider - addItemTranslations lines
    lang_changed = 0
    for m in pat.finditer(lang):
        old_c = m.group(1)
        if old_c not in en_map:
            continue

        old_line = m.group(0)
        zh = m.group(3)
        new_e = en_map[old_c]
        new_c = const_map.get(old_c, old_c)

        new_line = f'this.add(NMIFoodItems.{new_c}, "{escape_java_str(new_e)}", "{escape_java_str(zh)}");'

        if old_line != new_line:
            lang = lang.replace(old_line, new_line, 1)
            lang_changed += 1

    # Also update all NMIFoodItems.CONST references in NMILanguageProvider (descriptions, etc.)
    # Sort by length descending to avoid partial replacements
    ref_changed = 0
    for old_c, new_c in sorted(const_map.items(), key=lambda x: -len(x[0])):
        if old_c == new_c:
            continue
        new_lang, count = re.subn(
            rf'\bNMIFoodItems\.{re.escape(old_c)}\b',
            f'NMIFoodItems.{new_c}',
            lang
        )
        if count > 0:
            lang = new_lang
            ref_changed += count

    # Step 5-6: Update NMIFoodItems - constant declarations and register strings
    food_changed = 0
    for old_c, new_c in const_map.items():
        if old_c not in old_regs:
            continue

        old_reg = old_regs[old_c]
        new_reg = f'food/{new_c.lower()}'

        if old_c == new_c and old_reg == new_reg:
            continue

        old_line = f'public static final DeferredItem<Item> {old_c} = registerItem(ITEMS, "{old_reg}"'
        new_line = f'public static final DeferredItem<Item> {new_c} = registerItem(ITEMS, "{new_reg}"'

        if old_line in food:
            food = food.replace(old_line, new_line, 1)
            food_changed += 1
            log.append(f"[FOOD] {old_c} -> {new_c} | {old_reg} -> {new_reg} | en: {en_map.get(old_c, '?')}")
        else:
            log.append(f"[FOOD_MISS] {old_c}: declaration pattern not found in NMIFoodItems")

    # Step 7: Sync texture filenames
    tex_renamed = 0
    if os.path.exists(tex_dir):
        for old_c, new_c in const_map.items():
            if old_c not in old_regs:
                continue
            if old_c == new_c:
                continue

            old_reg = old_regs[old_c]
            old_name = os.path.basename(old_reg) + '.png'
            new_name = new_c.lower() + '.png'

            if old_name == new_name:
                continue

            op = os.path.join(tex_dir, old_name)
            np = os.path.join(tex_dir, new_name)

            if os.path.exists(op) and not os.path.exists(np):
                os.rename(op, np)
                tex_renamed += 1
            elif os.path.exists(np) and not os.path.exists(op):
                log.append(f"[TEX_OK] {old_name} -> {new_name} (already renamed)")
            elif not os.path.exists(op) and not os.path.exists(np):
                log.append(f"[TEX_MISSING] {old_name} (neither old nor new exists)")
            else:
                log.append(f"[TEX_CONFLICT] {old_name} -> {new_name} (both exist)")

    # Write modified files
    with open(lang_path, 'w', encoding='utf-8') as f:
        f.write(lang)
    with open(food_path, 'w', encoding='utf-8') as f:
        f.write(food)

    # Write log
    log_path = os.path.join(BASE, 'fix_log.txt')
    with open(log_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(log))

    # Summary
    renamed = sum(1 for o, n in const_map.items() if o != n)
    en_updated = sum(1 for o, n in en_map.items())
    print(f"=== Summary ===")
    print(f"Total food items processed: {len(const_map)}")
    print(f"Constants renamed: {renamed}")
    print(f"English translations updated: {en_updated}")
    print(f"addItemTranslations lines changed: {lang_changed}")
    print(f"Additional const references updated in lang: {ref_changed}")
    print(f"NMIFoodItems declarations changed: {food_changed}")
    print(f"Texture files renamed: {tex_renamed}")
    print(f"Log entries: {len(log)}")
    print(f"Log: {log_path}")


if __name__ == '__main__':
    main()
