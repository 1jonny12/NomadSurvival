package core.jcommandbuilder;

import java.util.List;

public class StaticJTabProvider implements JTabProvider{

    private final String[] tabs;

    public StaticJTabProvider(String... tabs) {
        this.tabs = tabs;
    }

    @Override
    public List<String> getTabs() {
        return List.of(tabs);
    }
}
