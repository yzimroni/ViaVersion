package us.myles.ViaVersion2.api.protocol1_9to1_8.types;


import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion2.api.metadata.Metadata;
import us.myles.ViaVersion2.api.protocol1_9to1_8.MetadataTypes;
import us.myles.ViaVersion2.api.type.Type;

public class MetadataType extends Type<Metadata> {

    public MetadataType() {
        super(Metadata.class);
    }

    @Override
    public Metadata read(ByteBuf buffer) throws Exception {
        byte item = buffer.readByte();
        if (item == 127) return null; // end of metadata
        MetadataTypes type = MetadataTypes.byId((item & 0xE0) >> 5);
        int id = item & 0x1F;
        return new Metadata(id, type.getType(), type.getType().read(buffer));
    }

    @Override
    public void write(ByteBuf buffer, Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(127);
        } else {
            buffer.writeByte(object.getId());
            object.getType().write(buffer, object.getValue());
        }
    }
}