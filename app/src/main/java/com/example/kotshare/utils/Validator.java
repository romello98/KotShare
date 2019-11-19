package com.example.kotshare.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.kotshare.R;

public class Validator
{
    /* Constants */
    public static final int PASSWORD_MIN_LENGTH = 8;

    /* Singleton unique instance */
    private static Validator validator;

    /* Validation conditions HashMaps */
    private static HashMap<Integer, Validable<String>> passwordValidityConditions = new HashMap<>();
    private static HashMap<Integer, Validable<String>> emailValidityConditions = new HashMap<>();

    private Validator()
    {
        /* Password validation conditions */
        passwordValidityConditions.put(R.string.error_password_length,
                (String password) -> password.length() >= PASSWORD_MIN_LENGTH);
        passwordValidityConditions.put(R.string.error_password_uppercase_letter,
                (String password) -> hasUppercaseLetter(password));
        passwordValidityConditions.put(R.string.error_password_lowercase_letter,
                (String password) -> hasLowercaseLetter(password));

        /* Email validation conditions */

        emailValidityConditions.put(R.string.error_email_format,
                (String email) -> isValidEmail(email));
    }

    public static Validator getInstance()
    {
        if (validator == null) validator = new Validator();
        return validator;
    }

    /* VALIDATION METHODS */

    public ArrayList<Integer> validatePassword(String password)
    {
        return validate(passwordValidityConditions, password);
    }

    public ArrayList<Integer> validateEmail(String email)
    {
        return validate(emailValidityConditions, email);
    }

    /* ANDROID ERROR CODE TO MESSAGE CONVERSION METHOD */

    public String errorCodeToMessage(Context context, Integer errorCode, Object... params)
    {
        return context.getString(errorCode, params);
    }

    /* ALL PRIVATE VALIDATION MECHANISMS */

    private boolean isValidEmail(String email)
    {
        return email.matches("\\w+\\@\\w+\\.\\w{2,}");
    }

    private <T> ArrayList<Integer> validate(HashMap<Integer, Validable<T>> validityConditions, T data)
    {
        ArrayList<Integer> errorCodes = new ArrayList<>();

        for(Map.Entry<Integer, Validable<T>> entry : validityConditions.entrySet())
            if(!entry.getValue().validate(data))
                errorCodes.add(entry.getKey());

        return errorCodes;
    }

    private boolean hasUppercaseLetter(String string)
    {
        return string.matches("[A-ZÀ-ÖØ-ÞĀĂĄĆĈĊČĎĐĒĔĖĘĚĜĞĠĢĤĦĨĪĬĮİĲĴĶĹĻĽĿŁŃŅŇŊŌŎŐŒŔŖŘŚŜŞŠŢŤŦŨŪŬŮŰŲŴŶŸ-ŹŻŽƁ-ƂƄƆ-ƇƉ-ƋƎ-ƑƓ-ƔƖ-ƘƜ-ƝƟ-ƠƢƤƦ-ƧƩƬƮ-ƯƱ-ƳƵƷ-ƸƼǄǇǊǍǏǑǓǕǗǙǛǞǠǢǤǦǨǪǬǮǱǴǶ-ǸǺǼǾȀȂȄȆȈȊȌȎȐȒȔȖȘȚȜȞȠȢȤȦȨȪȬȮȰȲȺ-ȻȽ-ȾɁɃ-ɆɈɊɌɎͰͲͶΆΈ-ΊΌΎ-ΏΑ-ΡΣ-ΫϏϒ-ϔϘϚϜϞϠϢϤϦϨϪϬϮϴϷϹ-ϺϽ-ЯѠѢѤѦѨѪѬѮѰѲѴѶѸѺѼѾҀҊҌҎҐҒҔҖҘҚҜҞҠҢҤҦҨҪҬҮҰҲҴҶҸҺҼҾӀ-ӁӃӅӇӉӋӍӐӒӔӖӘӚӜӞӠӢӤӦӨӪӬӮӰӲӴӶӸӺӼӾԀԂԄԆԈԊԌԎԐԒԔԖԘԚԜԞԠԢԱ-ՖႠ-ჅḀḂḄḆḈḊḌḎḐḒḔḖḘḚḜḞḠḢḤḦḨḪḬḮḰḲḴḶḸḺḼḾṀṂṄṆṈṊṌṎṐṒṔṖṘṚṜṞṠṢṤṦṨṪṬṮṰṲṴṶṸṺṼṾẀẂẄẆẈẊẌẎẐẒẔẞẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼẾỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴỶỸỺỼỾἈ-ἏἘ-ἝἨ-ἯἸ-ἿὈ-ὍὙὛὝὟὨ-ὯᾸ-ΆῈ-ΉῘ-ΊῨ-ῬῸ-Ώℂℇℋ-ℍℐ-ℒℕℙ-ℝℤΩℨK-ℭℰ-ℳℾ-ℿⅅↃⰀ-ⰮⱠⱢ-ⱤⱧⱩⱫⱭ-ⱯⱲⱵⲀⲂⲄⲆⲈⲊⲌⲎⲐⲒⲔⲖⲘⲚⲜⲞⲠⲢⲤⲦⲨⲪⲬⲮⲰⲲⲴⲶⲸⲺⲼⲾⳀⳂⳄⳆⳈⳊⳌⳎⳐⳒⳔⳖⳘⳚⳜⳞⳠⳢꙀꙂꙄꙆꙈꙊꙌꙎꙐꙒꙔꙖꙘꙚꙜꙞꙢꙤꙦꙨꙪꙬꚀꚂꚄꚆꚈꚊꚌꚎꚐꚒꚔꚖꜢꜤꜦꜨꜪꜬꜮꜲꜴꜶꜸꜺꜼꜾꝀꝂꝄꝆꝈꝊꝌꝎꝐꝒꝔꝖꝘꝚꝜꝞꝠꝢꝤꝦꝨꝪꝬꝮꝹꝻꝽ-ꝾꞀꞂꞄꞆꞋＡ-Ｚ]|\\ud801[\\udc00-\\udc27]|\\ud835[\\udc00-\\udc19\\udc34-\\udc4d\\udc68-\\udc81\\udc9c\\udc9e-\\udc9f\\udca2\\udca5-\\udca6\\udca9-\\udcac\\udcae-\\udcb5\\udcd0-\\udce9\\udd04-\\udd05\\udd07-\\udd0a\\udd0d-\\udd14\\udd16-\\udd1c\\udd38-\\udd39\\udd3b-\\udd3e\\udd40-\\udd44\\udd46\\udd4a-\\udd50\\udd6c-\\udd85\\udda0-\\uddb9\\uddd4-\\udded\\ude08-\\ude21\\ude3c-\\ude55\\ude70-\\ude89\\udea8-\\udec0\\udee2-\\udefa\\udf1c-\\udf34\\udf56-\\udf6e\\udf90-\\udfa8\\udfca]");
    }

    private boolean hasLowercaseLetter(String string)
    {
        return string.matches("[a-zªµºß-öø-ÿāăąćĉċčďđēĕėęěĝğġģĥħĩīĭįıĳĵķ-ĸĺļľŀłńņň-ŉŋōŏőœŕŗřśŝşšţťŧũūŭůűųŵŷźżž-ƀƃƅƈƌ-ƍƒƕƙ-ƛƞơƣƥƨƪ-ƫƭưƴƶƹ-ƺƽ-ƿǆǉǌǎǐǒǔǖǘǚǜ-ǝǟǡǣǥǧǩǫǭǯ-ǰǳǵǹǻǽǿȁȃȅȇȉȋȍȏȑȓȕȗșțȝȟȡȣȥȧȩȫȭȯȱȳ-ȹȼȿ-ɀɂɇɉɋɍɏ-ʓʕ-ʯͱͳͷͻ-ͽΐά-ώϐ-ϑϕ-ϗϙϛϝϟϡϣϥϧϩϫϭϯ-ϳϵϸϻ-ϼа-џѡѣѥѧѩѫѭѯѱѳѵѷѹѻѽѿҁҋҍҏґғҕҗҙқҝҟҡңҥҧҩҫҭүұҳҵҷҹһҽҿӂӄӆӈӊӌӎ-ӏӑӓӕӗәӛӝӟӡӣӥӧөӫӭӯӱӳӵӷӹӻӽӿԁԃԅԇԉԋԍԏԑԓԕԗԙԛԝԟԡԣա-ևᴀ-ᴫᵢ-ᵷᵹ-ᶚḁḃḅḇḉḋḍḏḑḓḕḗḙḛḝḟḡḣḥḧḩḫḭḯḱḳḵḷḹḻḽḿṁṃṅṇṉṋṍṏṑṓṕṗṙṛṝṟṡṣṥṧṩṫṭṯṱṳṵṷṹṻṽṿẁẃẅẇẉẋẍẏẑẓẕ-ẝẟạảấầẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹỻỽỿ-ἇἐ-ἕἠ-ἧἰ-ἷὀ-ὅὐ-ὗὠ-ὧὰ-ώᾀ-ᾇᾐ-ᾗᾠ-ᾧᾰ-ᾴᾶ-ᾷιῂ-ῄῆ-ῇῐ-ΐῖ-ῗῠ-ῧῲ-ῴῶ-ῷⁱⁿℊℎ-ℏℓℯℴℹℼ-ℽⅆ-ⅉⅎↄⰰ-ⱞⱡⱥ-ⱦⱨⱪⱬⱱⱳ-ⱴⱶ-ⱼⲁⲃⲅⲇⲉⲋⲍⲏⲑⲓⲕⲗⲙⲛⲝⲟⲡⲣⲥⲧⲩⲫⲭⲯⲱⲳⲵⲷⲹⲻⲽⲿⳁⳃⳅⳇⳉⳋⳍⳏⳑⳓⳕⳗⳙⳛⳝⳟⳡⳣ-ⳤⴀ-ⴥꙁꙃꙅꙇꙉꙋꙍꙏꙑꙓꙕꙗꙙꙛꙝꙟꙣꙥꙧꙩꙫꙭꚁꚃꚅꚇꚉꚋꚍꚏꚑꚓꚕꚗꜣꜥꜧꜩꜫꜭꜯ-ꜱꜳꜵꜷꜹꜻꜽꜿꝁꝃꝅꝇꝉꝋꝍꝏꝑꝓꝕꝗꝙꝛꝝꝟꝡꝣꝥꝧꝩꝫꝭꝯꝱ-ꝸꝺꝼꝿꞁꞃꞅꞇꞌﬀ-ﬆﬓ-ﬗａ-ｚ]|\\ud801[\\udc28-\\udc4f]|\\ud835[\\udc1a-\\udc33\\udc4e-\\udc54\\udc56-\\udc67\\udc82-\\udc9b\\udcb6-\\udcb9\\udcbb\\udcbd-\\udcc3\\udcc5-\\udccf\\udcea-\\udd03\\udd1e-\\udd37\\udd52-\\udd6b\\udd86-\\udd9f\\uddba-\\uddd3\\uddee-\\ude07\\ude22-\\ude3b\\ude56-\\ude6f\\ude8a-\\udea5\\udec2-\\udeda\\udedc-\\udee1\\udefc-\\udf14\\udf16-\\udf1b\\udf36-\\udf4e\\udf50-\\udf55\\udf70-\\udf88\\udf8a-\\udf8f\\udfaa-\\udfc2\\udfc4-\\udfc9\\udfcb]");
    }

    private interface Validable<T>
    {
        Boolean validate(T dataToValidate);
    }
}
