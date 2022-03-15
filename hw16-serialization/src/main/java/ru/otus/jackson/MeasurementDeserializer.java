package ru.otus.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementDeserializer extends StdDeserializer<Measurement> {

    public MeasurementDeserializer() {
        super(Measurement.class);
    }

    @Override
    public Measurement deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        return new Measurement(
            node.get("name").asText(),
            node.get("value").asDouble()
        );
    }
}
