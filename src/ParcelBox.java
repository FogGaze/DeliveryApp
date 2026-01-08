import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {
    private final double maxWeightBox;
    private double currentWeightBox;

    private ArrayList<T> parcelBoxContent = new ArrayList<>();

    public ArrayList<T> getParcelBoxContent() {
        return parcelBoxContent;
    }

    public ParcelBox(double maxWeightBox) {
        this.maxWeightBox = maxWeightBox;
        this.currentWeightBox = 0;
    }

    public void addParcel(T parcel) {
        if (maxWeightBox >= (currentWeightBox + parcel.getWeight())) {
            parcelBoxContent.add(parcel);
            currentWeightBox += parcel.getWeight();
            System.out.println("Посылка добавлена в коробку");
        } else {
            System.out.println("Коробка будет переполнена. Посылка не добавлена в коробку");
        }
    }

    public void getAllParcels() {
        for (T parcel : parcelBoxContent) {
            System.out.println(parcel.getDescription());
        }
    }

}