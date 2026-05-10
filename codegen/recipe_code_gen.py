import json
from dataclasses import dataclass
from typing import List, Dict
import csv


@dataclass
class From:
    self: bool
    bond: bool = False  # 给默认值更安全


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
        from_obj = From(
            self=(raw_from == "self"),
            bond=False
        )

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


@dataclass
class RecipeEntry:
    input: RegistryEntry
    cooker: str
    output: RegistryEntry
    time: int

# ✅ 构建中文 → Registry 的索引
def build_chinese_index(registry):
    index = {}
    for entry in registry.values():
        index[entry.chinese_name] = entry
    return index


# ✅ 查找（找不到就返回原字符串）
def resolve_item(name: str, index):
    entry = index.get(name)
    if entry:
        return f"Items.{entry.field_name}"
    return name  # fallback


# ✅ 输出菜品（用 cuisines）
def resolve_cuisine(name: str, index):
    entry = index.get(name)
    if entry:
        return f"NMICuisinesItems.{entry.field_name}"
    return name


if __name__ == "__main__":
    with open('data.json', 'r', encoding='utf-8') as f:
        data = json.load(f)
    recipes = [dict_to_recipe(item) for item in data]
    registry = {}

    with open('registry.csv', 'r', encoding='utf-8') as f:
        arr = list(csv.DictReader(f))
        for e in arr:
            entry = RegistryEntry(
                id=e["\ufeff注册id"],
                field_name=e["注册静态变量"],
                chinese_name=e["中文plain本地化"],
                english_name=e["英文plain本地化"]
            )
            registry[entry.id] = entry

        # ===== 建索引 =====
    chinese_index = build_chinese_index(registry)

    # ===== 生成代码 =====
    output_lines = []

    for r in recipes:
        # 输出菜品
        output_item = resolve_cuisine(r.name, chinese_index)

        # 输入材料（取第一个）
        if r.ingredients:
            input_item = resolve_item(r.ingredients[0], chinese_index)
        else:
            input_item = "UNKNOWN"

        line = f"""this.builder({output_item})
           .input({input_item})
           .kitchenware({r.cooker})
           .time({r.baseCookTime})
           .build();"""

        output_lines.append(line)

    # ===== 打印 =====
    print("\n\n".join(output_lines))

