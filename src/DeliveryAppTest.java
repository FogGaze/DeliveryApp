import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryAppTest {

    @Test
    public void calculatedDeliveryCostStandardParcels() {
        double sumPriceDeliveryStandardParcel = 0;
        double sumPriceDeliveryFragileParcel = 0;
        double sumPriceDeliveryPerishableParcel = 0;

        StandardParcel[] parcelsStandardParcel = new StandardParcel[10];
        FragileParcel[] parcelsFragileParcel = new FragileParcel[10];
        PerishableParcel[] parcelsPerishableParcel = new PerishableParcel[10];

        for (int i = 0; i < 10; i++) {
            parcelsStandardParcel[i] = new StandardParcel("Описание", i, "Адрес", 1);
            parcelsFragileParcel[i] = new FragileParcel("Описание", i, "Адрес", 1);
            parcelsPerishableParcel[i] = new PerishableParcel("Описание", i, "Адрес", 1, 1);

            sumPriceDeliveryStandardParcel += parcelsStandardParcel[i].calculateDeliveryCost();
            sumPriceDeliveryFragileParcel += parcelsFragileParcel[i].calculateDeliveryCost();
            sumPriceDeliveryPerishableParcel += parcelsPerishableParcel[i].calculateDeliveryCost();
        }
        assertEquals(90, sumPriceDeliveryStandardParcel);
        assertEquals(180, sumPriceDeliveryFragileParcel);
        assertEquals(135, sumPriceDeliveryPerishableParcel);
    }

    @Test
    public void isExpiring() {
        PerishableParcel perishableParcel = new PerishableParcel("Описание", 1, "Адрес", 1, 5);
        assertFalse(perishableParcel.isExpired(1));
        assertFalse(perishableParcel.isExpired(6));
        assertTrue(perishableParcel.isExpired(7));
    }

    @Test
    public void addInBox() {
        StandardParcel standardParcel = new StandardParcel("Описание", 1, "Адрес", 1);
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);
        parcelBox.addParcel(standardParcel);
        assertTrue(parcelBox.getParcelBoxContent().contains(standardParcel));

        StandardParcel standardParcelTwo = new StandardParcel("Описание", 9, "Адрес", 1);
        parcelBox.addParcel(standardParcelTwo);
        assertTrue(parcelBox.getParcelBoxContent().contains(standardParcelTwo));

        StandardParcel standardParcelThree = new StandardParcel("Описание", 1, "Адрес", 1);
        parcelBox.addParcel(standardParcelThree);
        assertFalse(parcelBox.getParcelBoxContent().contains(standardParcelThree));
    }

}
