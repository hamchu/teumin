package teumin.network;

public enum DataType {
    LOGIN_REQUEST,
    // [0] String id
    // [1] String password
    LOGIN_RESPOND,
    // [0] boolean success
    // [1] String name
    REGISTER_REQUEST,
    // [0] String id
    // [1] String password
    // [2] String name
    REGISTER_RESPOND,
    // [0] boolean success
    REGISTER_FOODTRUCK_REQUEST,
    // [0] String id
    // [1] String trademark_name
    // [2] String introduction
    // [3] blob trademark_icon ---
    // [4] int approval
    // [5] blob evidence_material ---
    // [6] String category
    REGISTER_FOODTRUCK_RESPOND,
    // [0] boolean success
    REGISTER_FOOD_ITEM_REQUEST, // item? product?
    // [0] String trademark_name
    // [1] String item_name
    // [2] String introduction
    // [3] blob image
    // [4] int price
    REGISTER_FOOD_ITEM_RESPOND,
    // [0] boolean success
    FOODTRUCK_INQUIRY_REQUEST,
    // [0] String category
    // [1] String address
    FOODTRUCK_INQUIRY_RESPOND,
    // [0] boolean null_point
    // [1] String food_truck_name
}
