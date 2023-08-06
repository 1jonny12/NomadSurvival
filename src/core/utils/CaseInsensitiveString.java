package core.utils;

public class CaseInsensitiveString {

    private String value;

    public CaseInsensitiveString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void add(String stringToAdd){
        value += stringToAdd;
    }

    public boolean isEqual(Object obj) {
        if (obj instanceof CaseInsensitiveString) {
            CaseInsensitiveString other = (CaseInsensitiveString) obj;
            return this.value.equalsIgnoreCase(other.value);
        }
        if (obj instanceof String) {
            String other = (String) obj;
            return this.value.equalsIgnoreCase(other);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CaseInsensitiveString) {
            CaseInsensitiveString other = (CaseInsensitiveString) obj;
            return this.value.equalsIgnoreCase(other.value);
        }
        if (obj instanceof String) {
            String other = (String) obj;
            return this.value.equalsIgnoreCase(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}