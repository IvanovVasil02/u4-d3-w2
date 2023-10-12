package vasilivanov.entities;

import java.util.Objects;

public class Customer {
  private Long id;
  private String name;
  private int tier;

  public Customer( String name) {
    this.id = (long) Math.floor(Math.random() * 101);
    this.name = name;
    this.tier =(int) Math.floor(Math.random() * 4);;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getTier() {
    return tier;
  }

  public void setTier(Integer tier) {
    this.tier = tier;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(tier, customer.tier);
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", tier=" + tier +
            '}';
  }
}
