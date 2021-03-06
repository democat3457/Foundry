package exter.foundry.fluid;

import exter.foundry.Foundry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidLiquidMetal extends Fluid
{

    private int color;

    public final boolean glass;

    public static final ResourceLocation STILL = new ResourceLocation(Foundry.MODID, "blocks/molten_metal_still");
    public static final ResourceLocation FLOW = new ResourceLocation(Foundry.MODID, "blocks/molten_metal_flow");

    public FluidLiquidMetal(String fluidName, ResourceLocation still, ResourceLocation flowing, int color, boolean glass, int temperature, int luminosity)
    {
        super(fluidName, still, flowing);
        this.color = color;
        this.glass = glass;
        setTemperature(temperature);
        setLuminosity(luminosity);
        setDensity(glass ? 2600 : 12000);
    }

    public FluidLiquidMetal(String fluidName, int color, boolean glass, int temperature, int luminosity)
    {
        super(fluidName, STILL, FLOW);
        this.color = color;
        this.glass = glass;
        setTemperature(temperature);
        setLuminosity(luminosity);
        setDensity(glass ? 2600 : 12000);
    }

    @Override
    public int getColor()
    {
        return color;
    }

    @Override
    public FluidLiquidMetal setColor(int fluid_color)
    {
        color = fluid_color | 0xFF000000;
        return this;
    }
}
