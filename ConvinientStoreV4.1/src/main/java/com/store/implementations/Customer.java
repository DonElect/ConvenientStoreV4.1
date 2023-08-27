package com.store.implementations;

import com.store.models.ProductDetails;
import com.store.services.CATEGORY;

import java.util.HashMap;
import java.util.Map;

import com.store.implementations.Products.*;

public class Customer {
    private Products products = new Products();

    public Customer(){
    }
    private Map<String, ProductDetails> cart = new HashMap<>();

    public Map<String, ProductDetails> getCart() {
        return cart;
    }

    public String addToCart(String item, int quantity) {
        item = item.toLowerCase();
        String result = productCheck(item);
        Map<String, ProductDetails> itemCategory = (result.equalsIgnoreCase("vegetables"))? products.getVegetables():
                (result.equalsIgnoreCase("provisions"))? products.getProvisions():
                        (result.equalsIgnoreCase("fruits"))? products.getFruits():
                                (result.equalsIgnoreCase("tools"))? products.getFruits(): null;
        if(itemCategory != null) {
            if(adjustQuantity(item, quantity)) {
                if (cart.containsKey(item)) {
                    int currentQuantity = cart.get(item).getQuantity() + quantity;
                    cart.get(item).setQuantity(currentQuantity);
                } else {
                    ProductDetails newProducts = new ProductDetails();
                    newProducts.setNameOfProduct(item);
                    newProducts.setCAT(itemCategory.get(item).getCAT());
                    newProducts.setPrice(itemCategory.get(item).getPrice());
                    newProducts.setQuantity(quantity);
                    cart.put(item, newProducts);
                }
                System.out.println(quantity+ " "+ item+" added to your cart.");
                System.out.println();
                return "DONE";
            }
            else {
                System.out.println("OUT OF STOCK");
                return "OUT-OF-STOCK";
            }
        }
        else
            System.out.println("We dont have "+item.toUpperCase()+" in our store for now.");
        return "NOT-IN-STORE";
    }

    public String removeFromCart(String item, int quantity) {
        item = item.toLowerCase();
        // Convert quantity to negative to be able to reuse the adjustQuantity method
        quantity = -1*quantity;
        if(cart.containsKey(item)){
            if(adjustQuantity(item, quantity)) {
                if (cart.get(item).getQuantity() + quantity > 0) {
                    cart.get(item).setQuantity(cart.get(item).getQuantity() + quantity);
                    System.out.println(-1*quantity + " " + item + " removed from your cart!");
                    System.out.println();
                } else {
                    cart.remove(item);
                }
                return "done";
            }
            else return "not-done";
        }
        else {
            System.out.println("You dont have " + item.toUpperCase() + " in your cart.");
            return "not-in-cart";
        }
    }


    public boolean buy(){
        int sum = 0;
        System.out.println("You about to pay for the following items in your list");
        System.out.println("---------------------------------------------------------");
        System.out.println("Products                  Price(₦\u200E)              Quantity");
        for(Map.Entry<String, ProductDetails> item : cart.entrySet()){
            System.out.printf("%-25s %-25s %-25s",item.getKey(),item.getValue().getPrice(),item.getValue().getQuantity());
            System.out.println();
            sum += item.getValue().getPrice() * item.getValue().getQuantity();
        }
        System.out.println("Total price: "+sum);
        System.out.println("Go over the counter and make payment. Cash or card payment is acceptable");
        System.out.println();
        return true;
    }

    public void view(CATEGORY CAT){
        products.view(CAT);
    }

    private String productCheck(String productName){
        return products.getProvisions().containsKey(productName)? "provisions":
                products.getVegetables().containsKey(productName)? "vegetables":
                        products.getFruits().containsKey(productName)? "fruits":
                                products.getTools().containsKey(productName)? "tools":"";
    }

    private boolean adjustQuantity(String item, int quantity){
        if(products.getFruits().containsKey(item)) {
            if((products.getFruits().get(item).getQuantity() - quantity) >= 0) {
                products.getFruits().get(item).setQuantity(products.getFruits().get(item).getQuantity() - quantity);
                return true;
            }
            else{
                System.out.println(item.toUpperCase()+"s remaining "+ products.getFruits().get(item).getQuantity());
                return false;
            }
        }
        else if(products.getProvisions().containsKey(item)){
            if((products.getProvisions().get(item).getQuantity() - quantity) >= 0) {
                products.getProvisions().get(item).setQuantity(products.getProvisions().get(item).getQuantity() - quantity);
                return true;
            }
            else {
                System.out.println(item.toUpperCase()+"s remaining "+ products.getProvisions().get(item).getQuantity());
                return false;
            }
        }
        else if(products.getTools().containsKey(item)){
            if((products.getTools().get(item).getQuantity() - quantity) >= 0) {
                products.getTools().get(item).setQuantity(products.getTools().get(item).getQuantity() - quantity);
                return true;
            }
            else {
                System.out.println(item.toUpperCase()+"s remaining "+ products.getTools().get(item).getQuantity());
                return false;
            }
        }
        else if(products.getVegetables().containsKey(item)){
            if((products.getVegetables().get(item).getQuantity() - quantity) >= 0) {
                products.getVegetables().get(item).setQuantity(products.getVegetables().get(item).getQuantity() - quantity);
                return true;
            }
            else {
                System.out.println(item.toUpperCase()+"s remaining "+ products.getVegetables().get(item).getQuantity());
                return false;
            }
        }
        else return false;
    }
}
