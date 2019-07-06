/**
 *  Thaumic Augmentation
 *  Copyright (c) 2019 TheCodex6824.
 *
 *  This file is part of Thaumic Augmentation.
 *
 *  Thaumic Augmentation is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Thaumic Augmentation is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Thaumic Augmentation.  If not, see <https://www.gnu.org/licenses/>.
 */

package thecodex6824.thaumicaugmentation.api.event;

import java.util.UUID;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import thecodex6824.thaumicaugmentation.api.warded.CapabilityWardStorage;
import thecodex6824.thaumicaugmentation.api.warded.ClientWardStorageValue;
import thecodex6824.thaumicaugmentation.api.warded.IWardStorage;
import thecodex6824.thaumicaugmentation.api.warded.IWardStorageClient;
import thecodex6824.thaumicaugmentation.api.warded.IWardStorageServer;

/**
 * Event fired for all ward-focus related things. Subscribing to this will notify the callback
 * for all subclasses of this event.
 * @author TheCodex6824
 */
@Cancelable
public abstract class BlockWardEvent<T extends IWardStorage> extends BlockEvent {

    protected T storage;
    
    @SuppressWarnings("unchecked")
    public BlockWardEvent(World world, BlockPos pos) {
        super(world, pos, world.getBlockState(pos));
        storage = (T) world.getChunk(pos).getCapability(CapabilityWardStorage.WARD_STORAGE, null);
    }
    
    public T getChunkWardStorage() {
        return storage;
    }
    
    /**
     * Event fired when a block is warded for the client.
     * @author TheCodex6824
     */
    public static class WardedClient extends BlockWardEvent<IWardStorageClient> {
        
        protected ClientWardStorageValue val;
        
        public WardedClient(World world, BlockPos pos, ClientWardStorageValue newVal) {
            super(world, pos);
            val = newVal;
        }
        
        public ClientWardStorageValue getNewOwner() {
            return val;
        }
        
    }
    
    /**
     * Event fired when a block is warded for the server.
     * @author TheCodex6824
     */
    public static class WardedServer extends BlockWardEvent<IWardStorageServer> {
        
        protected UUID val;
        
        public WardedServer(World world, BlockPos pos, UUID newVal) {
            super(world, pos);
            val = newVal;
        }
        
        public UUID getNewOwner() {
            return val;
        }
        
    }
    
    /**
     * Event fired when a block is dewarded for the client.
     * @author TheCodex6824
     */
    public static class DewardedClient extends BlockWardEvent<IWardStorageClient> {
        
        public DewardedClient(World world, BlockPos pos) {
            super(world, pos);
        }
        
    }
    
    /**
     * Event fired when a block is dewarded for the server.
     * @author TheCodex6824
     */
    public static class DewardedServer extends BlockWardEvent<IWardStorageServer> {
        
        public DewardedServer(World world, BlockPos pos) {
            super(world, pos);
        }
        
    }
    
}
