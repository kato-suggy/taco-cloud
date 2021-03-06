package tacos;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Order save(Order order);

    List<Order> findByDeliveryZip(String deliveryZip);

    /*
     * Query for all orders delivered to a given zip code in a given date range.
     */
    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date startDate, Data, endDate);

    /*
     * Example which ignores case on all string comparisons
     */
    List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(
            String deliveryTo, String deliveryCity);

    /*
     *Example where results are ordered by a specified column
     */
    List<Order> findByDeliveryCityOrderByDeliveryTo(String city);

    /*
     * Example of writing a custom query without using naming conventions
     */
    @Query("Order o where o.deliveryCity='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();
}
