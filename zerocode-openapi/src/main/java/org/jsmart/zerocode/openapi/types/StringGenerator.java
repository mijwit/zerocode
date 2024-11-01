package org.jsmart.zerocode.openapi.types;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.media.Schema;

public class StringGenerator extends PrimitiveGenerator {
	public StringGenerator(String name, Schema<?> schema) {
		super(name, schema);
	}

	@Override
	public JsonNode generateJsonValue() {
		return new ObjectMapper().createObjectNode()
				.textNode(hasEnum() ? getEnumItem().toString() : getRandomToken());
	}

	@Override
	public JsonNode generateUrlEncodedJsonValue() {
		return new ObjectMapper().createObjectNode() // only urlencode the enum values
				.textNode(hasEnum() ? urlEncode(getEnumItem().toString()) : getRandomToken());
	}
	
	private String getRandomToken() {
		// TODO core enhancement Add tokens to generate random date and datetime
		// Here we are always generating the current date/time value, that can lead to repeated values in a step
		if ("date-time".equals(getFormat()))
			return "${LOCAL.DATETIME.NOW:yyyy-MM-dd'T'HH:mm:ssZ}";
		else if ("date".equals(getFormat()))
			return "${LOCAL.DATE.NOW:yyyy-MM-dd}";
		else // for string and other formats fallback
			return "${RANDOM.STRING:12}";
	}

}
