package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static ArrayList<Trackable> parcelsTrackable = new ArrayList<>();
    private static ParcelBox<StandardParcel> parcelBoxStandard = new ParcelBox<>(100);
    private static ParcelBox<FragileParcel> parcelBoxFragile = new ParcelBox<>(100);
    private static ParcelBox<PerishableParcel> parcelBoxPerishable = new ParcelBox<>(100);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportStatus();
                    break;
                case 5:
                    showParcelBoxes();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Изменить местоположение отслеживаемых посылок");
        System.out.println("5 — Посмотреть содержимое коробок с посылками");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        // Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels

        System.out.println("Введите описание посылки");
        String description = scanner.nextLine();

        System.out.println("Введите адрес доставки посылки");
        String deliveryAddress = scanner.nextLine();

        double weight = 0;
        while (true) {
            System.out.println("Введите вес посылки");
            weight = Double.parseDouble(scanner.nextLine());
            if (weight <= 0) {
                System.out.println("Вес посылки должен быть больше 0");
            } else {
                break;
            }
        }

        int sendDay = 0;
        while (true) {
            System.out.println("Введите день отправки посылки");
            sendDay = Integer.parseInt(scanner.nextLine());
            if (sendDay <= 0) {
                System.out.println("День отправки должен быть больше 0");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Выберите тип посылки:");
            System.out.println("1 - Стандартная");
            System.out.println("2 - Хрупкая");
            System.out.println("3 - Скоропортящаяся");
            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 1:
                    StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
                    allParcels.add(standardParcel);
                    parcelBoxStandard.addParcel(standardParcel);
                    System.out.println("Стандартная посылка добавлена в список посылок");
                    return;

                case 2:
                    FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
                    allParcels.add(fragileParcel);
                    parcelsTrackable.add(fragileParcel);
                    parcelBoxFragile.addParcel(fragileParcel);
                    System.out.println("Хрупкая посылка добавлена в список посылок");
                    return;

                case 3:
                    int timeToLive = 0;
                    while (true) {
                        System.out.println("Введите срок хранения посылки в днях:");
                        timeToLive = Integer.parseInt(scanner.nextLine());
                        if (timeToLive <= 0) {
                            System.out.println("Срок хранения посылки должен быть больше 0");
                        } else {
                            break;
                        }
                    }
                    PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                    allParcels.add(perishableParcel);
                    parcelBoxPerishable.addParcel(perishableParcel);
                    System.out.println("Скоропортящаяся посылка добавлена в список посылок");
                    return;

                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void sendParcels() {
        // Пройти по allParcels, вызвать packageItem() и deliver()
        if (allParcels.isEmpty()) {
            System.out.println("У вас нет посылок");

        } else {
            for (Parcel parcel : allParcels) {
                parcel.packageItem();
                parcel.deliver();
            }
            System.out.println("Все посылки упакованы и отправлены");
        }
    }

    private static void calculateCosts() {
        // Посчитать общую стоимость всех доставок и вывести на экран
        if (allParcels.isEmpty()) {
            System.out.println("У вас нет посылок");

        } else {
            double sumDelivery = 0;
            for (Parcel parcel : allParcels) {
                sumDelivery = sumDelivery + parcel.calculateDeliveryCost();
            }
            System.out.println("Сумма доставки всех посылок: " + sumDelivery);
        }
    }

    private static void reportStatus() {
        if (parcelsTrackable.isEmpty()) {
            System.out.println("У вас нет отслеживаемых посылок");

        } else {
            for (Trackable parcel : parcelsTrackable) {
                System.out.println("Введите новое местоположение для посылки " + parcel.getDescription());
                String newLocation = scanner.nextLine();
                parcel.reportStatus(newLocation);
            }
        }
    }

    private static void showParcelBoxes() {
        System.out.println("Выберите коробку:");
        System.out.println("1 - Коробка с стандартными посылками");
        System.out.println("2 - Коробка с хрупкими посылками");
        System.out.println("3 - Коробка с скоропортящимися посылками");
        int command = Integer.parseInt(scanner.nextLine());

        switch (command) {
            case 1:
                if (parcelBoxStandard.getParcelBoxContent().isEmpty()) {
                    System.out.println("Коробка пустая");
                } else {
                    System.out.println("В коробке находится " + parcelBoxStandard.getParcelBoxContent().size() + " посылок");
                    parcelBoxStandard.printAllParcels();
                }
                break;

            case 2 :
                if (parcelBoxFragile.getParcelBoxContent().isEmpty()) {
                    System.out.println("Коробка пустая");
                } else {
                    System.out.println("В коробке находится " + parcelBoxFragile.getParcelBoxContent().size() + " посылок");
                    parcelBoxFragile.printAllParcels();
                }
                break;

            case 3 :
                if (parcelBoxPerishable.getParcelBoxContent().isEmpty()) {
                    System.out.println("Коробка пустая");
                } else {
                    System.out.println("В коробке находится " + parcelBoxPerishable.getParcelBoxContent().size() + " посылок");
                    parcelBoxPerishable.printAllParcels();
                }
                break;

            default:
                System.out.println("Неверный выбор");
        }
    }

}