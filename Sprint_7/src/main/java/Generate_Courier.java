import static utils.Utils.randomString;
public class Generate_Courier {
    public static Courier randomCourier() {
        return new Courier()
                .setLogin(randomString(5))
                .set.Password(randomString(8))
                .setFirstName(randomString(10));
    }
}
