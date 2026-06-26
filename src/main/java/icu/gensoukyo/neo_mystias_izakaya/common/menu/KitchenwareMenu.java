/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.menu;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CreativeCupboardBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigData;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KitchenwareMenu extends AbstractNMIMenu {
    private final ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private final ContainerData data;
    private KitchenwareBlockEntity kitchenwareBE;

    public KitchenwareMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos(), new SimpleContainerData(2));
    }

    public KitchenwareMenu(int containerId, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(NMIMenus.KITCHENWARE_MENU.get(), containerId);
        this.invStart = 6;
        this.data = data;
        this.addDataSlots(data);
        BlockEntity blockEntity = inventory.player.level().getBlockEntity(blockPos);
        if (blockEntity instanceof KitchenwareBlockEntity kitchenware) {
            this.kitchenwareBE = kitchenware;
            addItems(kitchenwareBE);
            addPlayerInventory(inventory, 36);
        }
    }

    protected void addItems(Container items) {
        for (int i = 0; i < 5; ++i) {
            addSlot(new Slot(items, i, 17 + i * 25, 110) {
                @Override
                public int getMaxStackSize(ItemStack pStack) {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return pStack.tags().anyMatch(itemTagKey -> itemTagKey.equals(NMIVanillaTags.INGREDIENT))
                            && !kitchenwareBE.getBlockState().getValue(BlockStateProperties.LIT);
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    if (FMLEnvironment.getDist().isClient()) {
                        NMIClientUtil.updateKitchenwareScreen();
                    }
                }
            });
        }

        addSlot(new Slot(items, 5, 180, 110) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void setChanged() {
                this.container.setChanged();
            }
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((level, pos) -> player.isWithinBlockInteractionRange(pos, 4.0F), true);
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (kitchenwareBE == null || !kitchenwareBE.canStartCooking()) return false;

        IzakayaMenu menu = NMICommonIzakayaUtil.getMenu(player);
        var kw = NMIKitchenware.REGISTRY.getValue(kitchenwareBE.getKitchenwareTypeId());
        if (kw == null) return false;

        // 筛选出当前厨具能制作的菜品
        List<Identifier> filtered = new ArrayList<>();
        for (Identifier cuisineId : menu.cuisines()) {
            NMIRecipeHolder holder = NMIDataAccessor.server().getRecipeMap().getRecipeMap().get(cuisineId);
            if (holder != null && holder.recipe().kitchenware().equals(kw.blockTagKey())) {
                filtered.add(cuisineId);
            }
        }
        if (buttonId < 0 || buttonId >= filtered.size()) return false;

        Identifier cuisineId = filtered.get(buttonId);
        NMIRecipeHolder holder = NMIDataAccessor.server().getRecipeMap().getRecipeMap().get(cuisineId);
        if (holder == null) return false;
        NMIRecipe recipe = holder.recipe();

        // 检测是否存在创造橱柜
        boolean hasCreativeCupboard = hasCreativeCupboard(player);

        // 从背包匹配食材（创造橱柜则直接创建物品，不消耗背包）
        List<Ingredient> requiredInputs = recipe.input();
        NonNullList<ItemStack> ingredients = NonNullList.withSize(5, ItemStack.EMPTY);
        boolean allFound = true;
        for (int i = 0; i < requiredInputs.size() && i < 5; i++) {
            if (hasCreativeCupboard) {
                // 创造模式：取 Ingredient 的第一个匹配物品作为默认实例
                var values = requiredInputs.get(i).getValues();
                if (values.size() > 0) {
                    ingredients.set(i, new ItemStack(values.get(0).value()));
                } else {
                    allFound = false;
                    break;
                }
            } else {
                boolean found = false;
                for (ItemStack invStack : player.getInventory().getNonEquipmentItems()) {
                    if (requiredInputs.get(i).test(invStack)) {
                        ingredients.set(i, invStack.split(1));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allFound = false;
                    break;
                }
            }
        }
        if (!allFound) {
            if (!hasCreativeCupboard) {
                for (ItemStack ing : ingredients) {
                    if (!ing.isEmpty()) player.getInventory().add(ing);
                }
            }
            return false;
        }

        // 归还厨具中已有物品
        for (ItemStack existing : kitchenwareBE.getIngredientItems()) {
            if (!existing.isEmpty()) {
                if (!player.getInventory().add(existing)) {
                    player.drop(existing, false);
                }
            }
        }

        kitchenwareBE.setIngredients(ingredients);
        return true;
    }

    /**
     * 检查玩家帽子中是否有绑定的创造橱柜。
     */
    private static boolean hasCreativeCupboard(Player player) {
        var hat = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.is(NMIMainItems.MYSTIAS_HAT)) return false;
        CanteenConfigData data = hat.get(NMIDataComponentTypes.CANTEEN_CONFIG);
        if (data == null) return false;
        var level = player.level();
        for (BlockPos pos : data.cupboardList()) {
            if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof CreativeCupboardBlockEntity) {
                return true;
            }
        }
        return false;
    }

}
