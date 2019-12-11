package csvbuilder;


public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new CommonCSVBuilder();
    }
}
