package enhancedportals.client.gui;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.block.BlockPortal;
import enhancedportals.client.gui.elements.ElementGlyphDisplay;
import enhancedportals.inventory.BaseContainer;
import enhancedportals.inventory.ContainerDialingAdd;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.packet.PacketGuiData;
import enhancedportals.network.packet.PacketRequestGui;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.tile.TileDialingDevice;
import enhancedportals.utility.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class GuiDialingAdd extends BaseGui
{
    public static final int CONTAINER_SIZE = 131;
    protected TileDialingDevice dial;
    protected GuiTextField text;
    protected boolean isEditing = false;
    protected int particleFrameType = -1, particleFrame, particleFrameCycle;
    protected int[] particleFrames = new int[]{0};
    protected ElementGlyphDisplay display;

    public GuiDialingAdd(TileDialingDevice d, EntityPlayer p)
    {
        super(new ContainerDialingAdd(d, p.inventory), CONTAINER_SIZE);
        dial = d;
        name = "gui.dialDevice";
        setHidePlayerInventory();
        allowUserInput = true;
        Keyboard.enableRepeatEvents(true);

        if (ClientProxy.saveTexture == null)
        {
            ClientProxy.saveTexture = new PortalTextureManager();
        }
    }

    protected GuiDialingAdd(BaseContainer container, int cSize)
    {
        super(container, cSize);
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException
    {
        if (!text.textboxKeyTyped(par1, par2))
        {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        text.updateCursorCounter();

        if (particleFrameCycle >= 20)
        {
            particleFrame++;
            particleFrameCycle = 0;

            if (particleFrame >= particleFrames.length)
            {
                particleFrame = 0;
            }
        }

        particleFrameCycle++;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        //todo GuiTextField is probably wrong
        text = new GuiTextField(0,getFontRenderer(), guiLeft + 7, guiTop + 18, 162, 20);
        text.setText(ClientProxy.saveName);
        text.setCursorPosition(0);

        display = new ElementGlyphDisplay(this, 7, 52, ClientProxy.saveGlyph);
        addElement(display);

        buttonList.add(new GuiButton(0, guiLeft + 7, guiTop + ySize - 27, 80, 20, Localization.get("gui.cancel")));
        buttonList.add(new GuiButton(1, guiLeft + xSize - 87, guiTop + ySize - 27, 80, 20, Localization.get("gui.save")));

        buttonList.add(new GuiButton(100, guiLeft + 57, guiTop + 83, 20, 20, ""));
        buttonList.add(new GuiButton(101, guiLeft + (xSize / 2 - 10), guiTop + 83, 20, 20, ""));
        buttonList.add(new GuiButton(102, guiLeft + 99, guiTop + 83, 20, 20, ""));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        text.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        super.drawGuiContainerBackgroundLayer(f, i, j);
        text.drawTextBox();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        getFontRenderer().drawString(Localization.get("gui.uniqueIdentifier"), 7, 43, 0x404040);
        getFontRenderer().drawString(Localization.get("gui.textures"), 7, 73, 0x404040);

        GL11.glColor3f(1f, 1f, 1f);

//        todo itemRender.renderWithColor = false;
        ItemStack frame = new ItemStack(BlockFrame.instance, 0, 0), portal = new ItemStack(BlockPortal.instance, 0, 0);
        Color frameColour = new Color(0xFFFFFF), portalColour = new Color(0xFFFFFF), particleColour = new Color(0x0077D8);
        int particleType = 0;

        if (ClientProxy.saveTexture != null)
        {
            frameColour = new Color(ClientProxy.saveTexture.getFrameColour());
            portalColour = new Color(ClientProxy.saveTexture.getPortalColour());
            particleColour = new Color(ClientProxy.saveTexture.getParticleColour());
            particleType = ClientProxy.saveTexture.getParticleType();

            if (ClientProxy.saveTexture.getFrameItem() != null)
            {
                frame = ClientProxy.saveTexture.getFrameItem();
            }

            if (ClientProxy.saveTexture.getPortalItem() != null)
            {
                portal = ClientProxy.saveTexture.getPortalItem();
            }

            if (particleFrameType != particleType)
            {
                particleFrameType = particleType;
                particleFrame = 0;
                particleFrameCycle = 0;
                particleFrames = ClientProxy.particleSets.get(ClientProxy.saveTexture.getParticleType()).frames;
            }
        }

        GL11.glColor3f(frameColour.getRed() / 255F, frameColour.getGreen() / 255F, frameColour.getBlue() / 255F);

        if (ClientProxy.saveTexture.hasCustomFrameTexture())
        {
//       todo     drawIconNoReset(ClientProxy.customFrameTextures.get(ClientProxy.saveTexture.getCustomFrameTexture()), 59, 85, 0);
        }
        else
        {
//       todo     drawItemStack(frame, 59, 85);
        }

        GL11.glColor3f(portalColour.getRed() / 255F, portalColour.getGreen() / 255F, portalColour.getBlue() / 255F);

        if (ClientProxy.saveTexture.hasCustomPortalTexture())
        {
//       todo     drawIconNoReset(ClientProxy.customPortalTextures.get(ClientProxy.saveTexture.getCustomPortalTexture()), 80, 85, 0);
        }
        else
        {
//       todo     drawItemStack(portal, 80, 85);
        }

        GL11.glColor3f(particleColour.getRed() / 255F, particleColour.getGreen() / 255F, particleColour.getBlue() / 255F);
        getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
        drawTexturedModalRect(101, 85, particleFrames[particleFrame] % 16 * 16, particleFrames[particleFrame] / 16 * 16, 16, 16);
        GL11.glColor3f(1f, 1f, 1f);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

        if (!isEditing)
        {
            ClientProxy.saveGlyph = null;
            ClientProxy.saveName = null;
            ClientProxy.saveTexture = null;
        }
        else
        {
            ClientProxy.saveName = text.getText();
        }

        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(dial, Reference.GuiEnums.GUI_DIAL.DIAL_B));
        }
        else if (button.id == 1) // save
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("name", text.getText());
            tag.setString("uid", ClientProxy.saveGlyph.getGlyphString());
            ClientProxy.saveTexture.writeToNBT(tag, "texture");
            EnhancedPortals.packetPipeline.sendToServer(new PacketGuiData(tag));
        }
        else if (button.id == 100)
        {
            isEditing = true;
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(dial, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_A));
        }
        else if (button.id == 101)
        {
            isEditing = true;
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(dial, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_B));
        }
        else if (button.id == 102)
        {
            isEditing = true;
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestGui(dial, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_C));
        }
    }
}
