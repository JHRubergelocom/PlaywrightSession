package session;

public class ELOSolutionArchiveData {
    private final String selectorSolutionTile;
    private final String selectorSolutionsFolder;
    public ELOSolutionArchiveData(String selectorSolutionTile, String selectorSolutionsFolder) {
        this.selectorSolutionTile = selectorSolutionTile;
        this.selectorSolutionsFolder = selectorSolutionsFolder;
    }
    public String getSelectorSolutionTile() {
        return selectorSolutionTile;
    }
    public String getSelectorSolutionsFolder() {
        return selectorSolutionsFolder;
    }

    @Override
    public String toString() {
        return "ELOSolutionArchiveData{" +
                "selectorSolutionTile='" + selectorSolutionTile + '\'' +
                ", selectorSolutionsFolder='" + selectorSolutionsFolder + '\'' +
                '}';
    }
}