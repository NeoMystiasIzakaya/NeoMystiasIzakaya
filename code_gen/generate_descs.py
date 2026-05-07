#!/usr/bin/env python3
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
NEW_ZH = ROOT / 'src' / 'generated' / 'resources' / 'assets' / 'neo_mystias_izakaya' / 'lang' / 'zh_cn.json'
OLD_CN = ROOT / 'code_gen' / 'cn_old.json'
OLD_EN = ROOT / 'code_gen' / 'en_old.json'
OUT_SNIPPET = ROOT / 'code_gen' / 'generated_descs.txt'
OUT_UNMATCH = ROOT / 'code_gen' / 'unmatched_desc.json'


def load_json(path: Path):
    if not path.exists():
        print(f'File not found: {path}')
        return {}
    with path.open('r', encoding='utf-8') as f:
        return json.load(f)


def main():
    new_zh = load_json(NEW_ZH)
    old_cn = load_json(OLD_CN)
    old_en = load_json(OLD_EN)

    # reverse map: chinese value -> old key
    rev_cn = {}
    for k, v in old_cn.items():
        if isinstance(v, str):
            rev_cn[v.strip()] = k

    snippets = []
    unmatched = {}

    produced = set()
    def to_constant(name: str) -> str:
        # convert snake/hyphen names to UPPER_SNAKE
        out = []
        for ch in name:
            if ch.isalnum():
                out.append(ch.upper())
            else:
                out.append('_')
        s = ''.join(out)
        # collapse multiple underscores
        while '__' in s:
            s = s.replace('__', '_')
        return s.strip('_')

    for key, val in new_zh.items():
        base_new_key = key[:-5] if key.endswith('.desc') else key
        if not base_new_key.startswith('item.neo_mystias_izakaya.'):
            continue
        parts = base_new_key.split('.')
        # expect structure: item.neo_mystias_izakaya.<category>.<id>
        if len(parts) < 4:
            continue
        category = parts[2]
        ident = parts[3] if len(parts) >=4 else ''
        cn_name = val.strip()
        old_item_key = rev_cn.get(cn_name)
        # determine target class
        if category == 'food':
            cls = 'NMIFoodItems'
        elif category == 'drink':
            cls = 'NMIDrinkItems'
        elif category == 'ingredient' or category == 'ingredient' or category == 'ingredient':
            cls = 'NMIIngredientItems'
        else:
            # fallback to NMIFoodItems for unknown categories
            cls = 'NMIFoodItems'

        const = to_constant(ident)
        if not const:
            unmatched[base_new_key] = cn_name
            continue

        snippet_key = base_new_key + '.desc'
        if snippet_key in produced:
            continue

        # lookup tooltip key in old files for descriptions
        tooltip_key = None
        if old_item_key:
            tooltip_key = old_item_key.replace('item.', 'tooltip.')
        en_desc = old_en.get(tooltip_key, "") if tooltip_key else ""
        cn_desc = old_cn.get(tooltip_key, "") if tooltip_key else ""
        if not cn_desc:
            cn_desc = cn_name

        esc_en = en_desc.replace('\\', '\\\\').replace('"', '\\"')
        esc_cn = cn_desc.replace('\\', '\\\\').replace('"', '\\"')
        line = f'        this.addDescribe({cls}.{const}, "{esc_en}", "{esc_cn}");'
        snippets.append(line)
        produced.add(snippet_key)

    # write outputs
    OUT_SNIPPET.parent.mkdir(parents=True, exist_ok=True)
    with OUT_SNIPPET.open('w', encoding='utf-8') as f:
        f.write('// Generated description lines for NMILanguageProvider.addItemDescTranslations\n')
        f.write('// Paste these into the addItemDescTranslations() method.\n')
        f.write('\n')
        for l in snippets:
            f.write(l + '\n')

    with OUT_UNMATCH.open('w', encoding='utf-8') as f:
        json.dump(unmatched, f, ensure_ascii=False, indent=2)

    print(f'Wrote {len(snippets)} snippets to {OUT_SNIPPET}')
    print(f'Wrote {len(unmatched)} unmatched entries to {OUT_UNMATCH}')


if __name__ == '__main__':
    main()
