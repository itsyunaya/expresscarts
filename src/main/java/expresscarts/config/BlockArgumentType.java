package expresscarts.config;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.xpple.betterconfig.util.WrappedArgumentType;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class BlockArgumentType extends WrappedArgumentType<Block, ResourceLocation> {

    public BlockArgumentType() {
        super(ResourceLocationArgument.id());
    }

    @Override
    public Block parse(StringReader reader) throws CommandSyntaxException {
        var initialCursor = reader.getCursor();
        var res = ResourceLocation.read(reader);
        var block = BuiltInRegistries.BLOCK.getOptional(res);
        if (block.isEmpty()) {
            reader.setCursor(initialCursor);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
        }
        return block.get();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(BuiltInRegistries.BLOCK.keySet(), builder);
    }
}
