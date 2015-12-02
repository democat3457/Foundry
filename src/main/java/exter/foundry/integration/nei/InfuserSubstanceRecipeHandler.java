package exter.foundry.integration.nei;



import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

import com.google.common.collect.ImmutableList;

import exter.foundry.api.FoundryAPI;
import exter.foundry.api.recipe.IInfuserSubstanceRecipe;
import exter.foundry.api.substance.ISubstanceGuiTexture;
import exter.foundry.api.substance.InfuserSubstance;
import exter.foundry.block.FoundryBlocks;
import exter.foundry.block.BlockFoundryMachine.EnumMachine;
import exter.foundry.gui.GuiMetalInfuser;
import exter.foundry.recipes.SubstanceGuiTexture;
import exter.foundry.recipes.manager.InfuserRecipeManager;
import exter.foundry.tileentity.TileEntityFoundryPowered;

public class InfuserSubstanceRecipeHandler extends FoundryRecipeHandlerSubstance
{
  private static final Rectangle SUBSTANCE_RECT = new Rectangle(71 - 5, 43 - 11, 8, 47);


  public static final ProgressBar PROGRESS = new ProgressBar(42 - 5, 59 - 11, 176, 53, 27, 15, 0, 30);

  public class CachedInfuserSubstanceRecipe extends CachedFoundryRecipeSubstance
  {

    private PositionedStack input;
    private InfuserSubstance substance;
    private int energy;
    

    public CachedInfuserSubstanceRecipe(IInfuserSubstanceRecipe recipe)
    {
      input = new PositionedStack(asItemStackOrList(recipe.getInput()), 19 - 5, 59 - 11, true);
      substance = recipe.getOutput();
      energy = recipe.getEnergyNeeded();
    }

    @Override
    public PositionedStack getIngredient()
    {
      randomRenderPermutation(input, cycleticks / 20);
      return input;
    }

    @Override
    public InfuserSubstance getSubstance()
    {
      return substance;
    }
    
    public int GetEnergyNeeded()
    {
      return energy;
    }
  }

  @Override
  public String getRecipeName()
  {
    return "Metal Infuser";
  }

  @Override
  public String getGuiTexture()
  {
    return "foundry:textures/gui/infuser.png";
  }

  @Override
  public void drawExtras(int recipe)
  {
    CachedInfuserSubstanceRecipe foundryRecipe = (CachedInfuserSubstanceRecipe) arecipes.get(recipe);
    int currentProgress = foundryRecipe.getAgeTicks() % 30;
    if(currentProgress > 0)
    {
      drawProgressBar(PROGRESS, currentProgress);
    }
    int times = FoundryAPI.INFUSER_SUBSTANCE_AMOUNT_MAX / foundryRecipe.getSubstance().amount;
    DrawSubstance(foundryRecipe.getSubstance(), (foundryRecipe.getAgeTicks() / 30) % (times));
  }
  
  private void DrawSubstance(InfuserSubstance substance,int multiplier)
  {
    if(substance != null && substance.amount > 0)
    {
      ISubstanceGuiTexture tex = InfuserRecipeManager.instance.GetSubstanceTexture(substance.type);
      if(tex != null)
      {
        GuiDraw.changeTexture(tex.getLocation());
        int height = (substance.amount * multiplier) * 47 / FoundryAPI.INFUSER_SUBSTANCE_AMOUNT_MAX;
        if(height > 47)
        {
          height = 47;
        }

        Rectangle rect = getSubstanceRect();
        setGLColor(tex.getColor());
        GuiDraw.drawTexturedModalRect(rect.x, rect.y + 47 - height, tex.getX(), tex.getY() + 47 - height, SubstanceGuiTexture.TEXTURE_WIDTH, height);
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
      }
    }    
  }

  public void loadAllRecipes()
  {
    for(IInfuserSubstanceRecipe recipe : InfuserRecipeManager.instance.getSubstanceRecipes())
    {
      addRecipe(recipe);
    }
  }

  @Override
  public void loadUsageRecipes(String outputId, Object... results)
  {
    if(outputId.equals("foundry.infuser.substance"))
    {
      loadAllRecipes();
    }
    if(outputId.equals("item") && results[0] instanceof ItemStack)
    {
      for(IInfuserSubstanceRecipe recipe : InfuserRecipeManager.instance.getSubstanceRecipes())
      {
        if(recipe.matchesRecipe((ItemStack) results[0]))
        {
          addRecipe(recipe);
        }
      }
    }
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results)
  {
    if(outputId.equals("foundry.infuser.substance"))
    {
      loadAllRecipes();
    }
    if(outputId.equals("foundry.substance"))
    {
      if(!(results[0] instanceof InfuserSubstance))
      {
        return;
      }
      InfuserSubstance sub = (InfuserSubstance)results[0];
      for(IInfuserSubstanceRecipe recipe : InfuserRecipeManager.instance.getSubstanceRecipes())
      {
        if(recipe.getOutput().type.equals(sub.type))
        {
          addRecipe(recipe);
        }
      }
    }
  }

  public void addRecipe(IInfuserSubstanceRecipe recipe)
  {
    if(!isEmptyOre(recipe.getInput()))
    {
      arecipes.add(new CachedInfuserSubstanceRecipe(recipe));
    }
  }

  @Override
  public ItemStack getMachineItem()
  {
    return FoundryBlocks.block_machine.asItemStack(EnumMachine.INFUSER);
  }
  
  @Override
  public Rectangle getRecipeRect()
  {
    return new Rectangle(42 - 5, 59 - 11, 22, 15);
  }

  @Override
  public void loadTransferRects()
  {
    transferRects.add(new RecipeTransferRect(new Rectangle(42 - 5, 59 - 11, 22, 15), "foundry.infuser.substance", new Object[0]));
  }

  @Override
  public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis()
  {
    return ImmutableList.<Class<? extends GuiContainer>> of(GuiMetalInfuser.class);
  }
  
  @Override
  public int recipiesPerPage()
  {
    return 1;
  }

  @Override
  public void drawBackground(int recipe)
  {
    GL11.glColor4f(1, 1, 1, 1);
    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 108);
  }

  @Override
  protected Rectangle getSubstanceRect()
  {
    return SUBSTANCE_RECT;
  }

  @Override
  public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe)
  {
    CachedInfuserSubstanceRecipe foundryRecipe = (CachedInfuserSubstanceRecipe) arecipes.get(recipe);
    if(isMouseOver(PROGRESS.asRectangle(), gui, recipe))
    {
      float energy = foundryRecipe.GetEnergyNeeded();
      currenttip.add(EnumChatFormatting.GRAY + "Required power: ");
      currenttip.add(EnumChatFormatting.AQUA + String.format("%.1f RF/t", energy / TileEntityFoundryPowered.RATIO_RF));
      currenttip.add(EnumChatFormatting.AQUA + String.format("%.1f EU/t", energy / TileEntityFoundryPowered.RATIO_EU));
    }
    return super.handleTooltip(gui, currenttip, recipe);
  }
}
