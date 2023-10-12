package vasilivanov;

import com.github.javafaker.Faker;
import vasilivanov.entities.Customer;
import vasilivanov.entities.Order;
import vasilivanov.entities.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static vasilivanov.entities.Order.isBetween;

public class Application {

    public static void main(String[] args) throws ParseException {

        Faker faker = new Faker(new Locale("ITALY"));

        Predicate<Product> isBabyCategory = product -> product.getCategory().equals("Baby");
        Predicate<Product> isMoreThan100 = product -> product.getPrice() > 100;
        Predicate<Product> isBoys = product -> product.getCategory().equals("Boys");;
        Predicate<Order> isTier2 = order -> order.getCustomer().getTier().equals(2);;

        Supplier<Product> ProductSupplier = () -> new Product(faker.commerce().productName(), faker.commerce().department(), faker.commerce().price(50, 300)) ;
        Supplier<Product> bookSupplier = () -> new Product(faker.book().title(),"Book: "+ faker.book().genre(), faker.commerce().price(50, 300)) ;
        Supplier<Product> boysProductSupplier = () -> new Product(faker.commerce().productName(),"Boys", faker.commerce().price(50, 300)) ;
        Supplier<Customer> customerSupplier = () -> new Customer(faker.name().firstName()+" "+faker.name().lastName()) ;

        List<Product> productList = new ArrayList<>();

        for (int i=0; i<10; i++){
            productList.add(bookSupplier.get());
            productList.add(boysProductSupplier.get());
        }
        for (int i=0; i<10; i++){
            productList.add(ProductSupplier.get());
        }

//        ex1
        List<Product> books = productList.stream().filter(isMoreThan100).toList();
        System.out.println("---------------------------EX1---------------------");
//        books.forEach(System.out::println);
        //        ex2
        List<Product> babyProducts = productList.stream().filter(isBabyCategory).toList();
        System.out.println("---------------------------EX2---------------------");
//        babyProducts.forEach(System.out::println);

        //        ex3
        List<Product> BoysProducts = productList.stream().filter(isBoys).map(product -> {
            product.setPrice(Math.floor(product.getPrice() / 100 * 10));
            return product;
        }).toList();
        System.out.println("---------------------------EX3---------------------");
//        BoysProducts.forEach(System.out::println);

        //        ex4
        System.out.println("---------------------------EX4---------------------");

        List<Customer> customerList = new ArrayList<>();
        Predicate<Order> isBetweenFebAndApr = order -> isBetween(order, "2021-02-01", "2021-04-01") ;

        for (int i=0; i<9; i++){
            customerList.add(customerSupplier.get());
        }
        Date date = new Date();
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2020");
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2023");

        List<Order> orderList = customerList.stream().map(customer -> new Order("state of wainting", faker.date().between( date1, date2), date, productList, customer)).toList();
        List<Order> tier2orderList = orderList.stream().filter(isTier2.and(isBetweenFebAndApr)).toList();

        tier2orderList.forEach(System.out::println);

    }
}
