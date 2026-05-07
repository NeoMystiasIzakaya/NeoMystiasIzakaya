const fs = require("fs");

const records = [];

class FileRec {
    input_file;
    prefix_name;
    class_name;

    constructor(input_file, prefix_name, class_name) {
        this.input_file = input_file;
        this.prefix_name = prefix_name;
        this.class_name = class_name;
    }
}

records.push(new FileRec("./drink_items.json", "drink", "ModDrinkItems"));
records.push(new FileRec("./food_items.json", "food", "ModFoodItems"));
records.push(new FileRec("./ingredient_items.json", "ingredient", "ModIngredientItems"));

for (let rec of records) {

    const data = fs.readFileSync(rec.input_file, "utf8");
    const jsonArray = JSON.parse(data);

    let output_code = `
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;

import net.minecraft.world.item.Item;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ${rec.class_name} {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static DeferredItem<Item> registerItem(
            DeferredRegister.Items register,
            String name,
            Function<Item.Properties, Item> func,
            UnaryOperator<Item.Properties> properties
    ) {
        return register.registerItem(name, func, properties);
    }

`;

    for (let str_id of jsonArray) {

        output_code += `    public static final DeferredItem<Item> ${str_id.toUpperCase()} = registerItem(ITEMS, "${rec.prefix_name}/${str_id}", Item::new, props -> props);

`;
    }

    output_code += `
}
`;

    fs.writeFileSync(`${rec.class_name}.java`, output_code);

    console.log(`Generated ${rec.class_name}.java`);
}