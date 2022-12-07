package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Set;
import java.util.Collection;

@ResponseBody
public class StatsResponse {
    @JsonProperty
    @ApiModelProperty(value = "keys")
    Set<String> keys;

    @JsonProperty
    @ApiModelProperty(value = "values")
    Collection<Integer> values;

    @JsonCreator
    public StatsResponse(Set<String> keys, Collection<Integer> values) {
        this.keys = keys;
        this.values = values;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public Collection<Integer> getValues() {
        return values;
    }

    public void setValues(Collection<Integer> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsResponse that = (StatsResponse) o;
        return Objects.equals(keys, that.keys) && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys, values);
    }

    @Override
    public String toString() {
        return "StatsResponse{" +
                "keys=" + keys +
                ", values=" + values +
                '}';
    }
}
