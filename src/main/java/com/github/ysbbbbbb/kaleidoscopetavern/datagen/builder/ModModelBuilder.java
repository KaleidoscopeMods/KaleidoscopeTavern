package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ModModelBuilder extends ModelBuilder<ModModelBuilder> {
    public ModModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }

    @Override
    public JsonObject toJson() {
        JsonObject root = super.toJson();
        if (!root.has("transform")) {
            return root;
        }

        // 修正 Forge 对 root transform 的错误写法
        JsonObject newTransform = new JsonObject();
        JsonObject oldTransform = root.get("transform").getAsJsonObject();

        if (!oldTransform.has("translation")) {
            newTransform.add("translation", writeVec3(new Vector3f()));
        } else {
            newTransform.add("translation", oldTransform.get("translation"));
        }

        if (!oldTransform.has("scale")) {
            newTransform.add("scale", writeVec3(new Vector3f(1)));
        } else {
            newTransform.add("scale", oldTransform.get("scale"));
        }

        if (!oldTransform.has("rotation")) {
            newTransform.add("right_rotation", writeQuaternion(new Quaternionf()));
        } else {
            newTransform.add("right_rotation", oldTransform.get("rotation"));
        }

        if (!oldTransform.has("post_rotation")) {
            newTransform.add("left_rotation", writeQuaternion(new Quaternionf()));
        } else {
            newTransform.add("left_rotation", oldTransform.get("post_rotation"));
        }

        root.add("transform", newTransform);
        return root;
    }

    private static JsonArray writeVec3(Vector3f vector) {
        JsonArray array = new JsonArray();
        array.add(vector.x());
        array.add(vector.y());
        array.add(vector.z());
        return array;
    }

    private static JsonArray writeQuaternion(Quaternionf quaternion) {
        JsonArray array = new JsonArray();
        array.add(quaternion.x());
        array.add(quaternion.y());
        array.add(quaternion.z());
        array.add(quaternion.w());
        return array;
    }
}
