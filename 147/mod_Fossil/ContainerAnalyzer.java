package mod_Fossil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
public class ContainerAnalyzer extends Container
{
    private TileEntityAnalyzer analyzer;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
    public ContainerAnalyzer(InventoryPlayer inventoryplayer, TileEntity tileentityanalyzer)
    {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        analyzer = (TileEntityAnalyzer) tileentityanalyzer;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                addSlotToContainer(new Slot(analyzer, j + i * 3, 20 + j * 18, 17 + i * 18));
            }
        }

        addSlotToContainer(new SlotFurnace(inventoryplayer.player, analyzer, 9, 116, 21));

        //addSlot(new Slot(tileentityfurnace, 0, 56, 17));
        //addSlot(new Slot(tileentityfurnace, 1, 56, 53));
        //addSlot(new SlotFurnace(inventoryplayer.player, tileentityfurnace, 2, 116, 35));
        for (int i = 0; i < 3; i++)
        {
            addSlotToContainer(new SlotFurnace(inventoryplayer.player, analyzer, 10 + i, 111 + 18 * i, 53));
        }

        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }
    }
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.analyzer.analyzerCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.analyzer.analyzerBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.analyzer.currentItemBurnTime);
    }
    public void updateCraftingResults()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);

            if (cookTime != analyzer.analyzerCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, analyzer.analyzerCookTime);
            }

            if (burnTime != analyzer.analyzerBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, analyzer.analyzerBurnTime);
            }

            if (itemBurnTime != analyzer.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, analyzer.currentItemBurnTime);
            }
        }

        cookTime = analyzer.analyzerCookTime;
        burnTime = analyzer.analyzerBurnTime;
        itemBurnTime = analyzer.currentItemBurnTime;
    }
    public void updateProgressBar(int i, int j)
    {
        if (i == 0)
        {
            analyzer.analyzerCookTime = j;
        }

        if (i == 1)
        {
            analyzer.analyzerBurnTime = j;
        }

        if (i == 2)
        {
            analyzer.currentItemBurnTime = j;
        }
    }
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return analyzer.isUseableByPlayer(entityplayer);
    }
    /*
    	public ItemStack func_82846_b(int i)
        {
            ItemStack itemstack = null;
            Slot slot = (Slot)inventorySlots.get(i);
            if(slot != null && slot.getHasStack())
            {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();
                if(i == 2)
                {
                    if(!mergeItemStack(itemstack1, 3, 39, true))
                    {
                        return null;
                    }
                } else
                if(i >= 3 && i < 30)
                {
                    if(!mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                } else
                if(i >= 30 && i < 39)
                {
                    if(!mergeItemStack(itemstack1, 3, 30, false))
                    {
                        return null;
                    }
                } else
                    if(!mergeItemStack(itemstack1, 3, 39, false))
                    {
                        return null;
                    }
                if(itemstack1.stackSize == 0)
                {
                    slot.putStack(null);
                } else
                {
                    slot.onSlotChanged();
                }
                if (itemstack1.stackSize == itemstack1.stackSize)
                {
                    return null;
                }
            }
            return itemstack;
        }
        */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 == 2)
            {
                if (!this.mergeItemStack(var5, 3, 39, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 != 1 && par2 != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null)
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(var5, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}