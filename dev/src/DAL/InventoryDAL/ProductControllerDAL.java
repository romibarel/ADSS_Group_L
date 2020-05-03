package DAL.InventoryDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductControllerDAL {
    private List<CategoryDAL> categoryDALS;
    private Map<Integer , DataSaleProductDAL> saleData; //<barCode, DataSaleProduct>

    public ProductControllerDAL(){
        this.categoryDALS = new ArrayList<>();
        this.saleData = new HashMap<>();
    }

    public List<CategoryDAL> getCategoryDALS(Connection conn) {
        //return categoryDALS;
        List<CategoryDAL> ret = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CATEGORIES");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret.add(createCategoryDALByName(rs.getString(1), conn));
            }
        }
        catch (Exception e){
            System.out.println("failed");
        }
        return ret;

    }

    private CategoryDAL createCategoryDALByName(String categoryName, Connection conn) {
        List<ProductDAL> p = new ArrayList<>();
        List<CategoryDAL> c = new ArrayList<>();
        CategoryDAL ret = new CategoryDAL(categoryName, c, p);
        List<String> cNames = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT PRODUCTS.* " +
                    "FROM CATEGORIES_OF_PRODUCTS INNER JOIN PRODUCTS ON CATEGORIES_OF_PRODUCTS.Barcode = PRODUCTS.Barcode " +
                    "WHERE CATEGORIES_OF_PRODUCTS.CName = '"+categoryName+"'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductDAL product = new ProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getInt(4),
                        rs.getInt(5), rs.getDate(6));
                p.add(product);
            }
            PreparedStatement stmt2 = conn.prepareStatement("SELECT SUB_CATEGORIES.Sub " +
                    "FROM SUB_CATEGORIES " +
                    "WHERE main = '"+categoryName+"'");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                cNames.add(rs2.getString(1));
            }
        }
        catch (Exception e){
            System.out.println("failed");
        }
        for (String name : cNames){
            CategoryDAL cat = createCategoryDALByName(name, conn);
            c.add(cat);
        }
        ret.setSubCategoriesDAL(c);
        ret.setProductListDAL(p);
        return ret;

    }

    public void setCategoryDALS(List<CategoryDAL> categoryDALS) {
        this.categoryDALS = categoryDALS;
    }

    public Map<Integer, DataSaleProductDAL> getSaleData(Connection conn) {
        //return saleData;
        Map <Integer, DataSaleProductDAL> ret = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DATA_SALE_PRODUCTS");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DataSaleProductDAL d = new DataSaleProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getDouble(3), rs.getDouble(4));
                ret.put(d.getBarCode(), d);
            }
        }
        catch (Exception e){
            System.out.println("failed");
        }
        return ret;
    }

    public void setSaleData(Map<Integer, DataSaleProductDAL> saleData) {
        this.saleData = saleData;
    }

    public void addCategory(CategoryDAL dal, Connection conn) {
        //this.categoryDALS.add(dal);
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO CATEGORIES VALUES (?)");
            stmt.setString(1, dal.getName());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            System.out.println("failed");
        }
    }

    public CategoryDAL getCategoryDALByName(String name){
        for (CategoryDAL category:this.categoryDALS) {
            if (category.getName().equals(name)){
                return category;
            }
        }
        return null; /*the category doesn't exists ->this case never happens because we reach DAL only if
                    exists but it won't harm*/
    }

    public void appendSubCategory(String main, String sub, Connection conn) {
        //CategoryDAL mainC = getCategoryDALByName(main);
        //CategoryDAL subC = getCategoryDALByName(sub);
        //mainC.appendSubCategory(subC);
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SUB_CATEGORIES VALUES (?,?)");
            stmt.setString(1, main);
            stmt.setString(2, sub);
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            System.out.println("failed");
        }
    }

    public void appendProductToCategoryDAL(String name, ProductDAL productDAL, Connection conn) {
        //CategoryDAL c = getCategoryDALByName(name);
        //c.appendProductToCategoryDAL(productDAL);
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO CATEGORIES_OF_PRODUCTS VALUES (?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, productDAL.getBarCode());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            System.out.println("failed");
        }
    }

    public void addNewDataSaleProduct(int barcode, DataSaleProductDAL dal, Connection conn) {
        //this.saleData.put(barcode, dal);
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DATA_SALE_PRODUCTS VALUES (?,?,?,?)");
            stmt.setInt(1, dal.getBarCode());
            stmt.setString(2, dal.getProductName());
            stmt.setDouble(3, dal.getPrice());
            stmt.setDouble(4, dal.getDiscount());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            System.out.println("failed");
        }
    }

    public void updateDataSaleProduct(int barcode, DataSaleProductDAL dataSaleProductDAL, Connection conn){
        //this.saleData.replace(barcode, dataSaleProductDAL);
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE DATA_SALE_PRODUCTS SET" +
                    " Name = " + dataSaleProductDAL.getProductName() + " Price = " + dataSaleProductDAL.getPrice() +"Discount = "+dataSaleProductDAL.getDiscount()
                    +"WHERE Barcode = " + dataSaleProductDAL.getBarCode());
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }

    public void deleteProductFromCategory (ProductDAL productDAL, CategoryDAL categoryDAL, Connection conn){
        categoryDAL.deleteProduct(productDAL, conn);
    }

    public void addNewProduct (ProductDAL productDAL, Connection conn){
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO PRODUCTS VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, productDAL.getBarCode());
            stmt.setString(2, productDAL.getProductName());
            stmt.setString(3, productDAL.getManufactor());
            stmt.setInt(4, productDAL.getAmount());
            stmt.setInt(5, productDAL.getMinAmount());
            stmt.setDate(6, (Date) productDAL.getNextSupplyTime());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            System.out.println("failed");
        }
    }//in this case we don't ave to do something, but whenever we'll add database so we use this function

    public void updateProduct(ProductDAL productDAL, Connection conn){
        //ProductDAL p = searchProduct(productDAL.getBarCode());
        //p.setBarCode(productDAL.getBarCode());
        //p.setAmount(productDAL.getAmount());
        //p.setManufactor(productDAL.getManufactor());
        //p.setMinAmount(productDAL.getMinAmount());
        //p.setNextSupplyTime(productDAL.getNextSupplyTime());
        //p.setProductName(productDAL.getProductName());
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE PRODUCTS SET" +
                    " Name = " + productDAL.getProductName() +
                    " Manufactor = " + productDAL.getManufactor() +
                            " Amount = "+productDAL.getAmount() +
                    " MinAmount = " + productDAL.getMinAmount()+
                    " NextSupplyTime = " + productDAL.getNextSupplyTime()
                    );
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }
    public ProductDAL searchProduct (int barCode, Connection conn){
        //for (CategoryDAL category : this.categoryDALS) {
        //    if (category.hasProduct(barCode)){
        //        return category.getProduct(barCode);
        //    }
        //}
        //return null; //the category doesn't exists
        ProductDAL ret = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE " +
                    " Barcode = " + barCode);
            ResultSet rs = stmt.executeQuery();
            ret = new ProductDAL(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4),
                    rs.getInt(5), rs.getDate(6));
        }
        catch (Exception e){
            System.out.println("failed");
        }
        return ret;
    }

    public void updateCategory (String categoryName, String newName, Connection conn){
        //CategoryDAL c = getCategoryDALByName(categoryDAL.getName());
        //if (c==null){return;}
        //c.setName(categoryDAL.getName());
        //c.setProductListDAL(categoryDAL.getProductListDAL());
        //c.setSubCategoriesDAL(categoryDAL.getSubCategoriesDAL());
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE CATEGORIES SET" +
                    " Name = " + newName +" WHERE Name = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE CATEGORIES_OF_PRODUCTS SET" +
                    " CName = " + newName +" WHERE CName = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE SUB_CATEGORIES SET" +
                    " Main = " + newName +" WHERE Main = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE SUB_CATEGORIES SET" +
                    " Sub = " + newName +" WHERE Sub = " + categoryName
            );
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }

    public void deleteCategory(String cName, Connection conn){
        //for(int i=0; i<this.categoryDALS.size(); i=i+1){
        //    if (this.categoryDALS.get(i).getName().equals(categoryDAL.getName())){
        //        this.categoryDALS.remove(i);
        //        return;
        //    }
        //}

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM CATEGORIES WHERE " +
                    " Name = " + cName);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DELETE FROM SUB_CATEGORIES WHERE " +
                    " Sub = " + cName);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DELETE FROM CATEGORIES_OF_PRODUCTS WHERE " +
                    " CName = " + cName);
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }

    public List<CategoryDAL> getMySubCategoriesDAL(String name, Connection conn) {
        List <CategoryDAL> ret = new ArrayList<>();
        List <String> cNames = new ArrayList<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT SUB_CATEGORIES.Sub " +
                    "FROM SUB_CATEGORIES " +
                    "WHERE main = '"+name+"'");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                cNames.add(rs2.getString(1));
            }
        }
        catch (Exception e){
            System.out.println("failed");
        }
        for (String n : cNames){
            CategoryDAL cat = createCategoryDALByName(n, conn);
            ret.add(cat);
        }
        return ret;
    }

    public List<ProductDAL> getProductListDAL(String name, Connection conn) {
        List<ProductDAL> ret = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT PRODUCTS.* " +
                    "FROM CATEGORIES_OF_PRODUCTS INNER JOIN PRODUCTS ON CATEGORIES_OF_PRODUCTS.Barcode = PRODUCTS.Barcode " +
                    "WHERE CATEGORIES_OF_PRODUCTS.CName = '"+name+"'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductDAL product = new ProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getInt(4),
                        rs.getInt(5), rs.getDate(6));
                ret.add(product);
            }
        }
        catch (Exception e){
            System.out.println("failed");
        }
        return ret;

    }
}
