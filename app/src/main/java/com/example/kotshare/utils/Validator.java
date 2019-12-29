package com.example.kotshare.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.example.kotshare.R;
import com.example.kotshare.controller.UserController;
import com.example.kotshare.model.form.StudentRoomForm;
import com.example.kotshare.model.form.UserForm;

public class Validator
{
    private static final int MIN_NAME_LENGTH = 10;
    private UserController userController = new UserController();

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MIN_UPPERCASE = 1;
    public static final int PASSWORD_MIN_LOWERCASE = 1;

    private static Validator validator;
    private static HashMap<String, Validable<String>> passwordValidityConditions = new HashMap<>();
    private static HashMap<String, Validable<String>> emailValidityConditions = new HashMap<>();
    private static HashMap<String, Validable<String>> dateValidityConditions = new HashMap<>();
    private static HashMap<String, Validable<String>> phoneNumberValidityConditions = new HashMap<>();
    private static HashMap<String, Validable<Integer>> priceValidityConditions = new HashMap<>();
    private static HashMap<String, Validable<String>> studentRoomNameValidityConditions =
            new HashMap<>();
    private static HashMap<String, Validable<Integer>> roommatesNumberValidityConditions =
            new HashMap<>();

    private Context context;

    private Validator(Context context)
    {
        passwordValidityConditions.put(
                context.getString(R.string.error_password_length, PASSWORD_MIN_LENGTH),
                (String password) -> password.length() >= PASSWORD_MIN_LENGTH);
        passwordValidityConditions.put(
                context.getString(R.string.error_password_uppercase_letter, PASSWORD_MIN_UPPERCASE),
                this::hasUppercaseLetter);
        passwordValidityConditions.put(
                context.getString(R.string.error_password_lowercase_letter, PASSWORD_MIN_LOWERCASE),
                this::hasLowercaseLetter);

        emailValidityConditions.put(
                context.getString(R.string.error_email_format),
                this::isValidEmail);

        dateValidityConditions.put(
                context.getString(R.string.error_date_format),
                this::isValidDate);

        phoneNumberValidityConditions.put(context.getString(R.string.error_phone_number_format),
                (String phoneNumber) -> phoneNumber.matches("(((\\+|00)\\d{2}|0)\\d{9})"));

        priceValidityConditions.put(context.getString(R.string.error_invalid_price),
                (Integer price) -> price != null && price > 0);

        studentRoomNameValidityConditions.put(context.getString(R.string.error_student_room_name,
                MIN_NAME_LENGTH), (String name) -> name != null && name.length() >= MIN_NAME_LENGTH);

        roommatesNumberValidityConditions.put(context.getString(R.string.error_roommates_number),
                (Integer number) -> number != null && number >= 0);

    }

    public static Validator getInstance(Context context)
    {
        if (validator == null) validator = new Validator(context);
        validator.context = context;
        return validator;
    }

    public HashSet<String> validatePassword(String password)
    {
        return validate(passwordValidityConditions, password);
    }

    public HashSet<String> validateEmail(String email)
    {
        return validate(emailValidityConditions, email);
    }

    public HashSet<String> validatePhoneNumber(String phoneNumber)
    {
        return validate(phoneNumberValidityConditions, phoneNumber);
    }

    public HashSet<String> validateDate(String date)
    {
        return validate(dateValidityConditions, date);
    }

    public HashSet<String> validatePrice(Integer price)
    {
        return validate(priceValidityConditions, price);
    }

    public HashSet<String> validateName(String name)
    {
        return validate(studentRoomNameValidityConditions, name);
    }

    public ArrayList<String> validateForm(UserForm userForm, String passwordConfirmation)
    {
        ArrayList<String> errors = new ArrayList<>();
        if(userForm.getFirstName().isEmpty())
            errors.add(context.getString(R.string.error_first_name));
        if(userForm.getLastName().isEmpty())
            errors.add(context.getString(R.string.error_last_name));
        if(userForm.getSchoolId() == null || userForm.getSchoolId() < 0)
            errors.add(context.getString(R.string.no_school_selected));
        errors.addAll(validateEmail(userForm.getEmail()));
        errors.addAll(validatePhoneNumber(userForm.getPhoneNumber()));
        errors.addAll(validatePassword(userForm.getPassword()));
        if(!arePasswordsEqual(userForm.getPassword(), passwordConfirmation))
            errors.add(context.getString(R.string.error_password_confirmation));
        return errors;
    }

    public ArrayList<String> validateStudentRoomForm(StudentRoomForm studentRoomForm)
    {
        ArrayList<String> errors = new ArrayList<>();
        errors.addAll(validatePrice(studentRoomForm.getMonthlyPrice()));
        errors.addAll(validateName(studentRoomForm.getTitle()));
        if(studentRoomForm.getCityId() == null)
            errors.add(context.getString(R.string.error_select_city));
        if(studentRoomForm.getStartRentingDate() == null)
            errors.add(context.getString(R.string.date_min) + " : " + context.getString(R.string.error_date_format));
        if(studentRoomForm.getEndRentingDate() == null)
            errors.add(context.getString(R.string.date_max) + " : " + context.getString(R.string.error_date_format));
        errors.addAll(validate(roommatesNumberValidityConditions, studentRoomForm.getNumberRoommate()));
        if(studentRoomForm.getStreet() == null || studentRoomForm.getStreet() == "")
            errors.add(context.getString(R.string.error_street_required));
        if(studentRoomForm.getStreetNumber() == null || studentRoomForm.getStreetNumber() == "")
            errors.add(context.getString(R.string.error_street_number_required));
        return errors;
    }

    public boolean arePasswordsEqual(String password, String passwordConfirmation)
    {
        return password != null && password.equals(passwordConfirmation);
    }

    public String errorCodeToMessage(Context context, Integer errorCode, Object... params)
    {
        return context.getString(errorCode, params);
    }

    private boolean isValidEmail(String email)
    {
        return email != null && !email.isEmpty() && email.matches("\\w+\\@\\w+\\.\\w{2,}");
    }

    private boolean isValidDate(String date)
    {
        return date != null && (date.isEmpty() || date.matches("\\d{2}/\\d{2}/\\d{4}"));
    }

    private <T> HashSet<String> validate(HashMap<String, Validable<T>> validityConditions, T data)
    {
        HashSet<String> errorCodes = new HashSet<>();

        for(Map.Entry<String, Validable<T>> entry : validityConditions.entrySet())
            if(!entry.getValue().validate(data))
                errorCodes.add(entry.getKey());

        return errorCodes;
    }

    private boolean hasUppercaseLetter(String string)
    {
        return string.matches(".*([A-ZÀ-ÖØ-ÞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĲĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘŚŜŞŠŢŤŦŨŪŬŮŰŲŴŶŸ-ŹŻŽƁ-ƂƄƆ-ƇƉ-ƋƎ-ƑƓ-ƔƖ-ƘƜ-ƝƟ-ƠƢƤƦ-ƧƩƬƮ-ƯƱ-ƳƵƷ-ƸƼǄǇǊǍǏǑǓǕǗǙǛǞǠǢǤǦǨǪǬǮǱǴǶ-ǸǺǼǾȀȂȄȆȈȊȌȎȐȒȔȖȘȚȜȞȠȢȤȦȨȪȬȮȰȲȺ-ȻȽ-ȾɁɃ-ɆɈɊɌɎͰͲͶΆΈ-ΊΌΎ-ΏΑ-ΡΣ-ΫϏϒ-ϔϘϚϜϞϠϢϤϦϨϪϬϮϴϷϹ-ϺϽ-ЯѠѢѤѦѨѪѬѮѰѲѴѶѸѺѼѾҀҊҌҎҐҒҔҖҘҚҜҞҠҢҤҦҨҪҬҮҰҲҴҶҸҺҼҾӀ-ӁӃӅӇӉӋӍӐӒӔӖӘӚӜӞӠӢӤӦӨӪӬӮӰӲӴӶӸӺӼӾԀԂԄԆԈԊԌԎԐԒԔԖԘԚԜԞԠԢԱ-ՖႠ-ჅḀḂḄḆḈḊḌḎḐḒḔḖḘḚḜḞḠḢḤḦḨḪḬḮḰḲḴḶḸḺḼḾṀṂṄṆṈṊṌṎṐṒṔṖṘṚṜṞṠṢṤṦṨṪṬṮṰṲṴṶṸṺṼṾẀẂẄẆẈẊẌẎẐẒẔẞẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼẾỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴỶỸỺỼỾἈ-ἏἘ-ἝἨ-ἯἸ-ἿὈ-ὍὙὛὝὟὨ-ὯᾸ-ΆῈ-ΉῘ-ΊῨ-ῬῸ-Ώℂℇℋ-ℍℐ-ℒℕℙ-ℝℤΩℨK-ℭℰ-ℳℾ-ℿⅅↃⰀ-ⰮⱠⱢ-ⱤⱧⱩⱫⱭ-ⱯⱲⱵⲀⲂⲄⲆⲈⲊⲌⲎⲐⲒⲔⲖⲘⲚⲜⲞⲠⲢⲤⲦⲨⲪⲬⲮⲰⲲⲴⲶⲸⲺⲼⲾⳀⳂⳄⳆⳈⳊⳌⳎⳐⳒⳔⳖⳘⳚⳜⳞⳠⳢꙀꙂꙄꙆꙈꙊꙌꙎꙐꙒꙔꙖꙘꙚꙜꙞꙢꙤꙦꙨꙪꙬꚀꚂꚄꚆꚈꚊꚌꚎꚐꚒꚔꚖꜢꜤꜦꜨꜪꜬꜮꜲꜴꜶꜸꜺꜼꜾꝀꝂꝄꝆꝈꝊꝌꝎꝐꝒꝔꝖꝘꝚꝜꝞꝠꝢꝤꝦꝨꝪꝬꝮꝹꝻꝽ-ꝾꞀꞂꞄꞆꞋＡ-Ｚ]|\\ud801[\\udc00-\\udc27]|\\ud835[\\udc00-\\udc19\\udc34-\\udc4d\\udc68-\\udc81\\udc9c\\udc9e-\\udc9f\\udca2\\udca5-\\udca6\\udca9-\\udcac\\udcae-\\udcb5\\udcd0-\\udce9\\udd04-\\udd05\\udd07-\\udd0a\\udd0d-\\udd14\\udd16-\\udd1c\\udd38-\\udd39\\udd3b-\\udd3e\\udd40-\\udd44\\udd46\\udd4a-\\udd50\\udd6c-\\udd85\\udda0-\\uddb9\\uddd4-\\udded\\ude08-\\ude21\\ude3c-\\ude55\\ude70-\\ude89\\udea8-\\udec0\\udee2-\\udefa\\udf1c-\\udf34\\udf56-\\udf6e\\udf90-\\udfa8\\udfca])+.*");
    }

    private boolean hasLowercaseLetter(String string)
    {
        return string.matches(".*([a-zªµºß-öø-ÿāăąćĉċčďđēĕėęěĝğġģĥħĩīĭįıĳĵķ-ĸĺļľŀłńņň-ŉŋōŏőœŕŗřśŝşšţťŧũūŭůűųŵŷźżž-ƀƃƅƈƌ-ƍƒƕƙ-ƛƞơƣƥƨƪ-ƫƭưƴƶƹ-ƺƽ-ƿǆǉǌǎǐǒǔǖǘǚǜ-ǝǟǡǣǥǧǩǫǭǯ-ǰǳǵǹǻǽǿȁȃȅȇȉȋȍȏȑȓȕȗșțȝȟȡȣȥȧȩȫȭȯȱȳ-ȹȼȿ-ɀɂɇɉɋɍɏ-ʓʕ-ʯͱͳͷͻ-ͽΐά-ώϐ-ϑϕ-ϗϙϛϝϟϡϣϥϧϩϫϭϯ-ϳϵϸϻ-ϼа-џѡѣѥѧѩѫѭѯѱѳѵѷѹѻѽѿҁҋҍҏґғҕҗҙқҝҟҡңҥҧҩҫҭүұҳҵҷҹһҽҿӂӄӆӈӊӌӎ-ӏӑӓӕӗәӛӝӟӡӣӥӧөӫӭӯӱӳӵӷӹӻӽӿԁԃԅԇԉԋԍԏԑԓԕԗԙԛԝԟԡԣա-ևᴀ-ᴫᵢ-ᵷᵹ-ᶚḁḃḅḇḉḋḍḏḑḓḕḗḙḛḝḟḡḣḥḧḩḫḭḯḱḳḵḷḹḻḽḿṁṃṅṇṉṋṍṏṑṓṕṗṙṛṝṟṡṣṥṧṩṫṭṯṱṳṵṷṹṻṽṿẁẃẅẇẉẋẍẏẑẓẕ-ẝẟạảấầẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹỻỽỿ-ἇἐ-ἕἠ-ἧἰ-ἷὀ-ὅὐ-ὗὠ-ὧὰ-ώᾀ-ᾇᾐ-ᾗᾠ-ᾧᾰ-ᾴᾶ-ᾷιῂ-ῄῆ-ῇῐ-ΐῖ-ῗῠ-ῧῲ-ῴῶ-ῷⁱⁿℊℎ-ℏℓℯℴℹℼ-ℽⅆ-ⅉⅎↄⰰ-ⱞⱡⱥ-ⱦⱨⱪⱬⱱⱳ-ⱴⱶ-ⱼⲁⲃⲅⲇⲉⲋⲍⲏⲑⲓⲕⲗⲙⲛⲝⲟⲡⲣⲥⲧⲩⲫⲭⲯⲱⲳⲵⲷⲹⲻⲽⲿⳁⳃⳅⳇⳉⳋⳍⳏⳑⳓⳕⳗⳙⳛⳝⳟⳡⳣ-ⳤⴀ-ⴥꙁꙃꙅꙇꙉꙋꙍꙏꙑꙓꙕꙗꙙꙛꙝꙟꙣꙥꙧꙩꙫꙭꚁꚃꚅꚇꚉꚋꚍꚏꚑꚓꚕꚗꜣꜥꜧꜩꜫꜭꜯ-ꜱꜳꜵꜷꜹꜻꜽꜿꝁꝃꝅꝇꝉꝋꝍꝏꝑꝓꝕꝗꝙꝛꝝꝟꝡꝣꝥꝧꝩꝫꝭꝯꝱ-ꝸꝺꝼꝿꞁꞃꞅꞇꞌﬀ-ﬆﬓ-ﬗａ-ｚ]|\\ud801[\\udc28-\\udc4f]|\\ud835[\\udc1a-\\udc33\\udc4e-\\udc54\\udc56-\\udc67\\udc82-\\udc9b\\udcb6-\\udcb9\\udcbb\\udcbd-\\udcc3\\udcc5-\\udccf\\udcea-\\udd03\\udd1e-\\udd37\\udd52-\\udd6b\\udd86-\\udd9f\\uddba-\\uddd3\\uddee-\\ude07\\ude22-\\ude3b\\ude56-\\ude6f\\ude8a-\\udea5\\udec2-\\udeda\\udedc-\\udee1\\udefc-\\udf14\\udf16-\\udf1b\\udf36-\\udf4e\\udf50-\\udf55\\udf70-\\udf88\\udf8a-\\udf8f\\udfaa-\\udfc2\\udfc4-\\udfc9\\udfcb])+.*");
    }

    private interface Validable<T>
    {
        Boolean validate(T dataToValidate);
    }
}
