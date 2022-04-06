package de.mcestasia.estasiamall.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelperMethods {

    public static List<UUID> convertStringListToUUIDList(List<String> originalList) {
        List<UUID> newList = new ArrayList<>();

        originalList.forEach(uuid -> newList.add(UUID.fromString(uuid)));

        return newList;
    }

}
