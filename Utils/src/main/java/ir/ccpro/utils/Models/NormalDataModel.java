package ir.ccpro.utils.Models;

import java.util.List;

public class NormalDataModel {
    public class Item {
        public long id;
        public String url;
        public int watingInPage;
    }

    public long id;
    public int deviceType;//0
    public List<Item> items;
}

