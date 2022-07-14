package Controller.Database.Serializer;

import Model.Data.IRecordData;
import Model.Data.VehicleData;
import Model.List.ModelList;
import Model.List.TransactionList;
import Model.List.UserList;
import Model.List.VehicleList;

import java.util.HashMap;

public class VehicleSerializer implements IDataRecordSerializer
{
    @Override
    public HashMap<String, String> serialize(IRecordData component)
    {
        var vehicle = (VehicleData) component;
        HashMap<String, String> map = new HashMap<>();
        int objIndex = VehicleList.get().getIndexForComponent(vehicle);
        int parentObjIndex = ModelList.get().getIndexForComponent(vehicle.getModel());

        int buyerObjIndex = -1;
        int sellerObjIndex = -1;

        if (vehicle.getBuyer() != null) { buyerObjIndex = UserList.get().getIndexForComponent(vehicle.getBuyer()); }
        if (vehicle.getSeller() != null) { sellerObjIndex = UserList.get().getIndexForComponent(vehicle.getSeller()); }

        map.put("vehicleId", String.valueOf(objIndex));
        map.put("parentModelId", String.valueOf(parentObjIndex));
        map.put("VIN", vehicle.getVIN());
        map.put("licensePlate", vehicle.getLicensePlate());
        map.put("color", vehicle.getColor());
        map.put("mileage", String.valueOf(vehicle.getMileage()));

        map.put("discount", String.valueOf(vehicle.getDiscount()));
        map.put("price", String.valueOf(vehicle.getPrice()));
        map.put("buyerUserId", String.valueOf(buyerObjIndex));
        map.put("sellerUserId", String.valueOf(sellerObjIndex));

        return map;
    }
}
