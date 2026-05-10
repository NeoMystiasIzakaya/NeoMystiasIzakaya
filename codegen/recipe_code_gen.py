import json
from dataclasses import dataclass
from typing import List
import csv
import os
from collections import defaultdict


@dataclass
class From:
    self: bool
    bond: bool = False


@dataclass
class Recipe:
    id: int
    recipeId: int
    name: str
    description: str
    ingredients: List[str]
    positiveTags: List[str]
    negativeTags: List[str]
    cooker: str
    baseCookTime: int
    dlc: int
    level: int
    price: int
    from_: From


def dict_to_recipe(d: dict) -> Recipe:
    raw_from = d.get("from", {})

    if isinstance(raw_from, dict):
        from_obj = From(
            self=raw_from.get("self", False),
            bond=raw_from.get("bond", False)
        )
    else:
        from_obj = From(self=(raw_from == "self"))

    return Recipe(
        id=d["id"],
        recipeId=d["recipeId"],
        name=d["name"],
        description=d["description"],
        ingredients=d["ingredients"],
        positiveTags=d["positiveTags"],
        negativeTags=d["negativeTags"],
        cooker=d["cooker"],
        baseCookTime=d["baseCookTime"],
        dlc=d["dlc"],
        level=d["level"],
        price=d["price"],
        from_=from_obj
    )


@dataclass
class RegistryEntry:
    id: str
    field_name: str
    chinese_name: str
    english_name: str


# ===== 索引 =====
def build_chinese_index(registry):
    return {e.chinese_name: e for e in registry.values()}


# ===== 解析物品 =====
def resolve_item(name: str, index):
    entry = index.get(name)
    if entry:
        return f"NMIIngredientItems.{entry.field_name}"
    return name  # fallback


# ===== 解析菜品 =====
def resolve_cuisine(name: str, index):
    entry = index.get(name)
    if entry:
        return f"NMICuisinesItems.{entry.field_name}"
    return name  # fallback


# ===== cooker 映射 =====
COOKER_MAP = {
    "煮锅": "NMIBlockTags.BOILING_POT",
    "平底锅": "NMIBlockTags.FRYPAN",
    "烤架": "NMIBlockTags.GRILL",
    "蒸锅": "NMIBlockTags.STEAMER"
}


def resolve_cooker(name: str):
    return COOKER_MAP.get(name, name)  # fallback


if __name__ == "__main__":
    # ===== 读取 JSON =====
    with open('data.json', 'r', encoding='utf-8') as f:
        data = json.load(f)
    recipes = [dict_to_recipe(item) for item in data]

    # ===== 读取 CSV =====
    registry = {}
    with open('registry.csv', 'r', encoding='utf-8-sig') as f:  # 修复 BOM
        reader = csv.DictReader(f)
        for e in reader:
            entry = RegistryEntry(
                id=e["注册id"],
                field_name=e["注册静态变量"],
                chinese_name=e["中文plain本地化"],
                english_name=e["英文plain本地化"]
            )
            registry[entry.id] = entry

    # ===== 建索引 =====
    chinese_index = build_chinese_index(registry)

    # ===== 按 cooker 分组 =====
    grouped_output = defaultdict(list)

    for r in recipes:
        output_item = resolve_cuisine(r.name, chinese_index)

        # ===== 多 input =====
        if r.ingredients:
            inputs = [resolve_item(i, chinese_index) for i in r.ingredients]
            input_lines = "\n".join([f"        .input({i})" for i in inputs])
        else:
            input_lines = "        .input(UNKNOWN)"

        cooker_raw = r.cooker
        cooker = resolve_cooker(cooker_raw)

        line = f"""this.builder({output_item})
{input_lines}
        .kitchenware({cooker})
        .time({r.baseCookTime})
        .build();"""

        grouped_output[cooker_raw].append(line)

    # ===== 输出目录 =====
    os.makedirs("output", exist_ok=True)

    # ===== 写入文件 =====
    for cooker_name, lines in grouped_output.items():
        # 文件名安全处理
        safe_name = cooker_name.replace("/", "_")

        filename = f"output/{safe_name}.txt"

        with open(filename, "w", encoding="utf-8") as f:
            f.write("\n\n".join(lines))

        print(f"已生成: {filename}")