package xyz.snaker.tq.datagen.provider;

import java.util.Locale;

import xyz.snaker.snakerlib.utility.tools.CollectionStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.tools.LanguageProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Languages extends LanguageProvider implements LanguageProviderTools<Languages>
{
    public Languages(PackOutput output)
    {
        super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
    }

    @Override
    public void addTranslations()
    {
        CollectionStuff.mapDeferredRegistries(Blocks.REGISTER, Block[]::new).forEach(this::block);
        CollectionStuff.mapDeferredRegistries(Items.REGISTER, Item[]::new).forEach(this::item);
        CollectionStuff.mapDeferredRegistries(Entities.REGISTER, EntityType<?>[]::new).forEach(this::entity);
        CollectionStuff.mapDeferredRegistries(Sounds.REGISTER, SoundEvent[]::new).forEach(this::sound);
        CollectionStuff.mapDeferredRegistries(Tabs.REGISTER, CreativeModeTab[]::new).forEach(this::tab);

        addAtlasTranslations();
        addMiscTranslations();
    }

    @Override
    public Languages getInstance()
    {
        return this;
    }
}
