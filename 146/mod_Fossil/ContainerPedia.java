package mod_Fossil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPedia extends Container
{
    public ContainerPedia()
    {
        super();
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }
}
