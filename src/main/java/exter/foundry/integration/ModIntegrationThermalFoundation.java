package exter.foundry.integration;

import static exter.foundry.api.FoundryAPI.FLUID_AMOUNT_INGOT;

import com.google.common.collect.ImmutableList;

import cofh.core.fluid.BlockFluidInteractive;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.init.TFFluids;
import cofh.thermalfoundation.init.TFProps;
import exter.foundry.api.FoundryAPI;
import exter.foundry.api.recipe.matcher.ItemStackMatcher;
import exter.foundry.api.recipe.matcher.OreMatcher;
import exter.foundry.config.FoundryConfig;
import exter.foundry.fluid.FoundryFluidRegistry;
import exter.foundry.fluid.FoundryFluids;
import exter.foundry.item.ItemMold;
import exter.foundry.item.ItemMold.SubItem;
import exter.foundry.recipes.manager.AlloyMixerRecipeManager;
import exter.foundry.recipes.manager.FluidHeaterFuelManager;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import exter.foundry.recipes.manager.MoldRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ModIntegrationThermalFoundation implements IModIntegration
{
    private static final String THERMALFOUNDATION = "thermalfoundation";

    @Override
    public String getName()
    {
        return THERMALFOUNDATION;
    }

    @Override
    public void onAfterPostInit()
    {
    }

    @Override
    public void onClientInit()
    {
    }

    @Override
    public void onClientPostInit()
    {
    }

    @Override
    public void onClientPreInit()
    {
    }

    @Override
    public void onInit()
    {
        //Redstone
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("dustRedstone"),
                new FluidStack(TFFluids.fluidRedstone, 100));
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("blockRedstone"),
                new FluidStack(TFFluids.fluidRedstone, 900));
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("clathrateRedstone"),
                new FluidStack(TFFluids.fluidRedstone, 250));
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("oreClathrateRedstone"),
                new FluidStack(TFFluids.fluidRedstone, 1000));
        if (OreDictionary.doesOreNameExist("dustSmallRedstone"))
        {
            MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("dustSmallRedstone"),
                    new FluidStack(TFFluids.fluidRedstone, 25));
        }
        //Glowstone
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("dustGlowstone"),
                new FluidStack(TFFluids.fluidGlowstone, 250), TFFluids.fluidGlowstone.getTemperature(), 90);
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("glowstone"),
                new FluidStack(TFFluids.fluidGlowstone, 1000), TFFluids.fluidGlowstone.getTemperature(), 90);
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("clathrateGlowstone"),
                new FluidStack(TFFluids.fluidGlowstone, 250), TFFluids.fluidGlowstone.getTemperature(), 90);
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("oreClathrateGlowstone"),
                new FluidStack(TFFluids.fluidGlowstone, 1000), TFFluids.fluidGlowstone.getTemperature(), 90);
        //Ender Pearl
        MeltingRecipeManager.INSTANCE.addRecipe(new ItemStackMatcher(Items.ENDER_PEARL),
                new FluidStack(TFFluids.fluidEnder, 250), TFFluids.fluidEnder.getTemperature(), 75);
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("clathrateEnder"),
                new FluidStack(TFFluids.fluidEnder, 250), TFFluids.fluidEnder.getTemperature(), 75);
        MeltingRecipeManager.INSTANCE.addRecipe(new OreMatcher("oreClathrateEnder"),
                new FluidStack(TFFluids.fluidEnder, 1000), TFFluids.fluidEnder.getTemperature(), 75);

        Fluid liquid_lumium = FluidRegistry.getFluid("lumium");
        if (FoundryFluidRegistry.getStrategy("lumium").registerRecipes() && liquid_lumium != null
                && FoundryFluids.liquid_tin != null && FoundryFluids.liquid_silver != null)
            AlloyMixerRecipeManager.INSTANCE.addRecipe(new FluidStack(liquid_lumium, FLUID_AMOUNT_INGOT),
                    new FluidStack(TFFluids.fluidGlowstone, 250),
                    new FluidStack(FoundryFluids.liquid_tin, FLUID_AMOUNT_INGOT / 4 * 3),
                    new FluidStack(FoundryFluids.liquid_silver, FLUID_AMOUNT_INGOT / 4));

        Fluid liquid_signalum = FluidRegistry.getFluid("signalum");
        if (FoundryFluidRegistry.getStrategy("signalum").registerRecipes() && liquid_signalum != null
                && FoundryFluids.liquid_copper != null && FoundryFluids.liquid_silver != null)
            AlloyMixerRecipeManager.INSTANCE.addRecipe(new FluidStack(liquid_signalum, FLUID_AMOUNT_INGOT),
                    new FluidStack(TFFluids.fluidRedstone, 250),
                    new FluidStack(FoundryFluids.liquid_copper, FLUID_AMOUNT_INGOT / 4 * 3),
                    new FluidStack(FoundryFluids.liquid_silver, FLUID_AMOUNT_INGOT / 4));

        Fluid liquid_enderium = FluidRegistry.getFluid("enderium");
        if (FoundryFluidRegistry.getStrategy("enderium").registerRecipes() && liquid_enderium != null
                && FoundryFluids.liquid_tin != null && FoundryFluids.liquid_silver != null
                && FoundryFluids.liquid_platinum != null)
            AlloyMixerRecipeManager.INSTANCE.addRecipe(new FluidStack(liquid_enderium, FLUID_AMOUNT_INGOT),
                    new FluidStack(TFFluids.fluidEnder, 250),
                    new FluidStack(FoundryFluids.liquid_lead, FLUID_AMOUNT_INGOT / 4 * 3),
                    new FluidStack(FoundryFluids.liquid_platinum, FLUID_AMOUNT_INGOT / 4));

        FluidHeaterFuelManager.INSTANCE.addFuel(TFFluids.fluidPyrotheum);

        MoldRecipeManager.INSTANCE.addRecipe(ItemMold.SubItem.SICKLE.getMatcher().getItem(), 5, 6,
                new int[] { 3, 3, 3, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 2, 3, 3, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 });
        MoldRecipeManager.INSTANCE.addRecipe(ItemMold.SubItem.HAMMER.getMatcher().getItem(), 5, 6,
                new int[] { 3, 2, 2, 2, 3, 3, 4, 4, 4, 3, 3, 2, 3, 2, 3, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 });
        MoldRecipeManager.INSTANCE.addRecipe(ItemMold.SubItem.EXCAVATOR.getMatcher().getItem(), 6, 6, new int[] { 0, 0, 0, 3, 3, 3, 0, 0, 3, 3,
                3, 3, 0, 3, 3, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0 });

        Configuration cfg = ThermalFoundation.CONFIG.getConfiguration();
        for (String name : ImmutableList.of("copper", "tin", "silver", "lead", "aluminum", "nickel", "platinum",
                "steel", "electrum", "invar", "bronze", "constantan", "iron", "gold"))
        {
            tryAddToolArmorRecipes(cfg, name, FluidRegistry.getFluid(name.equals("aluminum") ? "aluminium" : name));
        }

        BlockFluidInteractive pyrotheum = (BlockFluidInteractive) TFFluids.blockFluidPyrotheum;
        BlockFluidInteractive mana = (BlockFluidInteractive) TFFluids.blockFluidMana;

        pyrotheum.addInteraction(Blocks.SAND, FoundryFluids.liquid_glass.getBlock());
        pyrotheum.addInteraction(Blocks.GLASS, FoundryFluids.liquid_glass.getBlock());
        pyrotheum.addInteraction(Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, true),
                Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, false));
        mana.addInteraction(FoundryFluids.liquid_glass.getBlock(), Blocks.SAND);
        mana.addInteraction(Blocks.STAINED_GLASS, Blocks.SAND);

        IBlockState stained_glass = Blocks.STAINED_GLASS.getDefaultState();
        for (EnumDyeColor dye : EnumDyeColor.values())
        {
            int meta = dye.getMetadata();
            stained_glass = stained_glass.withProperty(BlockColored.COLOR, dye);
            pyrotheum.addInteraction(stained_glass, FoundryFluids.liquid_glass_colored[meta].getBlock());
            mana.addInteraction(FoundryFluids.liquid_glass_colored[meta].getBlock(), Blocks.SAND);
        }
    }

    @Override
    public void onPostInit()
    {
    }

    @Override
    public void onPreInit(Configuration config)
    {
    }

    public static void tryAddToolArmorRecipes(Configuration cfg, String name, Fluid fluid)
    {
        if (fluid == null || !FoundryFluidRegistry.getStrategy(name).registerRecipes())
        {
            return;
        }
        final String tools = "Equipment.Tools." + StringHelper.titleCase(name);
        final String armor = "Equipment.Armor." + StringHelper.titleCase(name);
        OreMatcher stick = new OreMatcher("stickWood", 2);

        if (Loader.isModLoaded("thermalfoundation"))
        {
            if (FoundryConfig.register_tf_tool_recipes_anyway || !TFProps.disableAllArmor)
            {
                Item helm = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "armor.helmet_" + name));
                if (helm != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(armor, "Helmet", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(helm),
                            new FluidStack(fluid, FoundryAPI.getAmountHelm()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(helm),
                            new FluidStack(fluid, FoundryAPI.getAmountHelm()), SubItem.HELMET, false, null);
                }

                Item chest = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "armor.plate_" + name));
                if (chest != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(armor, "Chestplate", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(chest),
                            new FluidStack(fluid, FoundryAPI.getAmountChest()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(chest),
                            new FluidStack(fluid, FoundryAPI.getAmountChest()), SubItem.CHESTPLATE, false, null);
                }

                Item legs = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "armor.legs_" + name));
                if (legs != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(armor, "Leggings", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(legs),
                            new FluidStack(fluid, FoundryAPI.getAmountLegs()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(legs),
                            new FluidStack(fluid, FoundryAPI.getAmountLegs()), SubItem.LEGGINGS, false, null);
                }

                Item boots = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "armor.boots_" + name));
                if (boots != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(armor, "Boots", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(boots),
                            new FluidStack(fluid, FoundryAPI.getAmountBoots()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(boots),
                            new FluidStack(fluid, FoundryAPI.getAmountBoots()), SubItem.BOOTS, false, null);
                }
            }

            if (FoundryConfig.register_tf_tool_recipes_anyway || !TFProps.disableAllTools)
            {
                if (FoundryConfig.register_tf_tool_recipes_anyway || !TFProps.disableAllShears)
                {
                    Item shears = ForgeRegistries.ITEMS
                            .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.shears_" + name));
                    if (shears != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Shears", true).getBoolean(true)))
                    {
                        FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(shears),
                                new FluidStack(fluid, FoundryAPI.getAmountShears()));
                        FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(shears),
                                new FluidStack(fluid, FoundryAPI.getAmountShears()), SubItem.SHEARS, false, null);
                    }
                }

                if (FoundryConfig.register_tf_tool_recipes_anyway || !TFProps.disableAllShields)
                {
                    Item shield = ForgeRegistries.ITEMS
                            .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.shield_" + name));
                    if (shield != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Shield", true).getBoolean(true)))
                    {
                        FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(shield),
                                new FluidStack(fluid, FoundryAPI.getAmountShield()));
                        FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(shield),
                                new FluidStack(fluid, FoundryAPI.getAmountShield()),
                                new ItemStackMatcher(new ItemStack(Items.SHIELD)), true, null);
                    }
                }

                Item pickaxe = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.pickaxe_" + name));
                if (pickaxe != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Pickaxe", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(pickaxe),
                            new FluidStack(fluid, FoundryAPI.getAmountPickaxe()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(pickaxe),
                            new FluidStack(fluid, FoundryAPI.getAmountPickaxe()), SubItem.PICKAXE, false, stick);
                }

                Item axe = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.axe_" + name));
                if (axe != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Axe", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(axe),
                            new FluidStack(fluid, FoundryAPI.getAmountAxe()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(axe),
                            new FluidStack(fluid, FoundryAPI.getAmountAxe()), SubItem.AXE, false, stick);
                }

                Item shovel = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.shovel_" + name));
                if (shovel != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Shovel", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(shovel),
                            new FluidStack(fluid, FoundryAPI.getAmountShovel()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(shovel),
                            new FluidStack(fluid, FoundryAPI.getAmountShovel()), SubItem.SHOVEL, false, stick);
                }

                Item hoe = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.hoe_" + name));
                if (hoe != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Hoe", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(hoe),
                            new FluidStack(fluid, FoundryAPI.getAmountHoe()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(hoe),
                            new FluidStack(fluid, FoundryAPI.getAmountHoe()), SubItem.HOE, false, stick);
                }

                Item sword = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.sword_" + name));
                if (sword != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Sword", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(sword),
                            new FluidStack(fluid, FoundryAPI.getAmountSword()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(sword),
                            new FluidStack(fluid, FoundryAPI.getAmountSword()), SubItem.SWORD, false,
                            new OreMatcher("stickWood"));
                }

                Item sickle = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.sickle_" + name));
                if (sickle != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Sickle", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(sickle),
                            new FluidStack(fluid, FoundryAPI.getAmountSickle()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(sickle),
                            new FluidStack(fluid, FoundryAPI.getAmountSickle()), SubItem.SICKLE, false,
                            new OreMatcher("stickWood"));
                }

                Item hammer = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.hammer_" + name));
                if (hammer != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Hammer", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(hammer),
                            new FluidStack(fluid, FoundryAPI.getAmountHammer()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(hammer),
                            new FluidStack(fluid, FoundryAPI.getAmountHammer()), SubItem.HAMMER, false, stick);
                }

                Item excavator = ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation(ThermalFoundation.MOD_ID, "tool.excavator_" + name));
                if (excavator != null && (FoundryConfig.register_tf_tool_recipes_anyway || cfg.get(tools, "Excavator", true).getBoolean(true)))
                {
                    FoundryAPI.MELTING_MANAGER.addRecipe(new ItemStackMatcher(excavator),
                            new FluidStack(fluid, FoundryAPI.getAmountExcavator()));
                    FoundryAPI.CASTING_MANAGER.addRecipe(new ItemStackMatcher(excavator),
                            new FluidStack(fluid, FoundryAPI.getAmountExcavator()), SubItem.EXCAVATOR, false, stick);
                }
            }
        }
    }

    @SubscribeEvent
    public void registerFluids(RegistryEvent.Register<Block> e)
    {
        IForgeRegistry<Block> registry = e.getRegistry();

        FoundryFluidRegistry.registerLiquidMetal(registry, "invar", THERMALFOUNDATION, 1780, 15, 0x7F907F);
        FoundryFluidRegistry.registerLiquidMetal(registry, "signalum", THERMALFOUNDATION, 2800, 15, 0xD84100);
        FoundryFluidRegistry.registerLiquidMetal(registry, "lumium", THERMALFOUNDATION, 2500, 15, 0xFFFF7F);
        FoundryFluidRegistry.registerLiquidMetal(registry, "enderium", THERMALFOUNDATION, 3800, 12, 0x007068);
    }
}
