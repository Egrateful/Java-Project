package uuu.resort.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {

    public static final char MALE = 'M';
    public static final char FEMALE = 'F';
    public static final DateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * id為必要欄位,且符合ROC ID規則
     */
    private String id; //non-static attribute, object/instance attribte

    /**
     * name為必要欄位
     */
    private String name;

    /**
     * gender為必要欄位, M: male, F: female
     */
    private char gender;

    /**
     * 密碼為必要欄位, 長度: 6~20
     */
    private String password;

    /**
     * email為必要欄位
     */
    private String email;

    private Date birthday; //須import java.util.Date
    private String phone;
    private String address;
    private boolean married;// = true;
    private BloodType bloodType;

    /**
     * status為內控欄位
     */
    private int status;//0:新客戶

    //Constructors
    public Customer() {
    }

    public Customer(String id, String name, String password) throws ResortException {
        this.setId(id);
        this.setName(name);
        this.setPassword(password);
//        this(id, name, 'M', password, null);
    }

    public Customer(String id, String name,
            char gender, String password, String email) throws ResortException {
//        this.setId(id);
//        this.setName(name);
//        this.setPassword(password);
        this(id, name, password);
        this.setGender(gender);
        this.setEmail(email);

    }

    //setter & getter:
    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getId() {//non-static method, object/instance method
        return id;
    }

    public void setId(String value) throws ResortException {
        if (checkId(value)) {
            id = value;
        } else {
            System.out.println("Id不正確");
            throw new ResortException("Id不正確");
        }
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) throws ResortException {
        if (gender == MALE || gender == FEMALE) {
            this.gender = gender;
        } else {
            System.out.println("性別資料不正確,必須為" + MALE + ":男性, 或" + FEMALE + ":女性!");
            throw new ResortException("性別資料不正確,必須為" + MALE + ":男性, 或" + FEMALE + ":女性!");
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) throws ResortException {
        if (name != null && (name = name.trim()).length() > 0) {
            this.name = name;
        } else {
            System.out.println("姓名為必要欄位!");
            throw new ResortException("姓名為必要欄位!");
        }
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) throws ResortException {
        if (password != null
                && (password = password.trim()).length() >= 6 && password.length() <= 20) {
            this.password = password;
        } else {
            System.out.println("密碼為必要欄位, 長度應在6~20之間!");
            throw new ResortException("密碼為必要欄位, 長度應在6~20之間!");
        }
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) throws ResortException {
        if (email != null
                && (email = email.trim()).matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            this.email = email;
        } else {
            System.out.println("email為必要欄位, 須符合正確格式!");
            throw new ResortException("email為必要欄位, 須符合正確格式!");
        }
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) throws ResortException {
        if (birthday != null && birthday.before(new Date())) {
            this.birthday = birthday;
        } else if (birthday == null) {
            this.birthday = null;
        } else {
            System.out.println("出生日期必須在今天以前");
            throw new ResortException("出生日期必須在今天以前");
        }
    }

    public void setBirthday(int year, int month, int day) throws ResortException {
        Date d = new GregorianCalendar(year, month - 1, day).getTime();
        this.setBirthday(d);
    }

    public void setBirthday(String date) throws ResortException {
        if (date == null || (date = date.trim()).length() == 0) {
            this.birthday = null;
            return;
        }
        try {
            //DateFormat df = DateFormat.getDateInstance();//yyyy/M/d
            date = date.replace('/', '-');
            //DateFormat df = new SimpleDateFormat("yyyy/M/d");//yyyy/M/d
            Date d = birthdayFormat.parse(date);
            this.setBirthday(d);
        } catch (ParseException ex) {
            Logger.getLogger(Customer.class.getName()).log(
                    Level.SEVERE, "日期格式不正確!", ex);
            throw new ResortException("日期格式不正確!", ex);
        }
    }

    public String getBirthdayString() {
        if (this.birthday != null) {
            return birthdayFormat.format(this.birthday);
        } else {
            return "";
        }
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        if (phone != null && (phone = phone.trim()).length() > 0) {
            this.phone = phone;
        } else {
            this.phone = null;
        }
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        if (address != null && (address = address.trim()).length() > 0) {
            this.address = address;
        } else {
            this.address = null;
        }
    }

    /**
     * @return the married
     */
    public boolean isMarried() {
        return married;
    }

    /**
     * @param married the married to set
     */
    public void setMarried(boolean married) {
        this.married = married;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    //business method
    public int getAge() {
        if (this.birthday == null) {
            return 0;
        }

        Calendar c = Calendar.getInstance();
        int thisYear = c.get(Calendar.YEAR);
        System.out.println("thisYear = " + thisYear);

        c.setTime(this.birthday);
        int birthYear = c.get(Calendar.YEAR);
        System.out.println("birthYear = " + birthYear);

        return thisYear - birthYear;
    }

    public static final String idPattern = "^[A-Z][12]\\d{8}$";

    public static boolean checkId(String idValue) {
        //1. 檢查格式
        if (idValue == null) {
            return false;
        }
        idValue = idValue.trim().toUpperCase();
        if (idValue.matches(idPattern)) { //regular expression            

            char lastChar = getLastCharFromId(idValue.substring(0, 9));

            //4. 將總和 mod 10 餘數為0: 正確
            System.out.println("lastChar = " + lastChar);
            return (lastChar == idValue.charAt(9));
        }
        return false;
    }

    private static final int[] firstChars = new int[]{10, 11, 12, 13, 14, 15, //A ~ F
        16, 17, 34, 18, 19, 20, 21, 22, 35, 23, //G ~ P
        24, 25, 26, 27, 28, 29, 32, 30, 31, 33}; //Q ~ Z

    public static char getLastCharFromId(String id9) {
        //2. 將第一個英文字元轉為對應的數字
        char firstChar = id9.charAt(0);
        int index = firstChar - 'A';

        int firstNumber = 0;
        if (index >= 0 && index < firstChars.length) {
            firstNumber = firstChars[index];
        } else {
            assert false : "身分證號首碼不正確: " + firstChar;
        }
        //System.out.println("firstNumber = " + firstNumber);
        assert (firstNumber >= 10 && firstNumber <= 35) : "firstNumber = " + firstNumber;

        //3. 依據規則加總
        int sum = (firstNumber / 10) + (firstNumber % 10) * 9;
        for (int i = 8; i >= 1; i--) {
            sum = sum + (id9.charAt(9 - i) - '0') * i;
        }
        char lastChar = (char) ((10 - (sum % 10)) % 10 + '0');  //sum = sum + (id9.charAt(9) - '0') * 1;
        assert (lastChar >= '0' && lastChar <= '9') : "身分證號末碼計算不正確: (" + lastChar + ")";
        return lastChar;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name
                + ", gender=" + gender + ", password=" + password + ", email=" + email
                + ", birthday=" + birthday + ", phone=" + phone + ", address=" + address
                + ", 血型=" + bloodType
                + ", married=" + married + ", status=" + status + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);//JDK 7.0
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.id, other.id)) {//JDK 7.0
            return false;
        }
        return true;
    }

}
