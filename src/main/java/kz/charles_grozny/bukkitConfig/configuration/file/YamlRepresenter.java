package kz.charles_grozny.bukkitConfig.configuration.file;

import java.util.LinkedHashMap;
import java.util.Map;

import kz.charles_grozny.bukkitConfig.configuration.ConfigurationSection;
import kz.charles_grozny.bukkitConfig.configuration.serialization.ConfigurationSerializable;
import kz.charles_grozny.bukkitConfig.configuration.serialization.ConfigurationSerialization;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

public class YamlRepresenter extends Representer {

    public YamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
    }

    private class RepresentConfigurationSection extends RepresentMap {
        @Override
        public Node representData(Object data) {
            return super.representData(((ConfigurationSection) data).getValues(false));
        }
    }

    private class RepresentConfigurationSerializable extends RepresentMap {
        @Override
        public Node representData(Object data) {
            ConfigurationSerializable serializable = (ConfigurationSerializable) data;
            Map<String, Object> values = new LinkedHashMap<String, Object>();
            values.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());

            return super.representData(values);
        }
    }
}
