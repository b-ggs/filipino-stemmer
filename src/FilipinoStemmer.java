import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by boggs on 9/12/15.
 */

public class FilipinoStemmer {
    public Reader reader = new Reader();
    public Writer writer = new Writer();
    public Scanner scanner = new Scanner(System.in);
    public Searcher searcher;

    public void init() {
        ArrayList<File> files = reader.getFiles();
        ArrayList<String> articles = new ArrayList<>();
//        ArrayList<String> words = new ArrayList<String>();

        for(int i = 0; i < files.size(); i++) {
//        for(int i = 0; i < 1; i++) {
            System.out.println("Reading from file " + (i + 1) + " of " + files.size() + ": " + files.get(i).toString() + ".");
//            words.addAll(reader.getNodes(files.get(i)));
            articles.add(reader.getArticle(files.get(i)));
        }

//        String article1 = "Tinatapatan mula pa kamakalawa ng gabi ng mga puwersang kalahok sa pangalawang people power revolution o EDSA 2 na nagpatalsik kay dating Pangulong Joseph Estrada sa pamamagitan ng rally sa paanan ng Don Chino Roces Bridge (dating Mendiola) ang demonstrasyon ng kanyang mga tagasuporta sa EDSA Shrine. Kagabi ay isinagawa ng mga kalahok sa EDSA 2 ang kanilang vigil sa Mendiola bilang pagsalubong sa pagdiriwang sa Araw ng paggawa ngayong Mayo 1. Inaasahang pangungunahan ng running priest na si Fr. Robert Reyes ang vigil sa pamamagitan ng isang banal na misa. Inaasahan ding isang milyong manggagawa at iba pang grupo ang magtitipon sa Mendiola para ipahayag na nananatiling buhay at ipaglalaban ang diwa ng EDSA 2 na nagluklok kay Pangulong Gloria Macapagal Arroyo sa puwesto. Sinusuportahan namin si Manila Archbishop Jaime Cardinal Sin sa panawagan niyang magtungo sa Mendiola para ipaglaban ang diwa ng People Power 2, sabi ni Reyes. Isusulong din nila ang pagpapatuloy ng mga kasong kriminal laban kay Estrada. Isasagawa ng mga grupong Bagong Alyansang Makabayan, Kilusang Mayo Uno, Trade Union Congress of the Philippines, Couples for Christ, 13-0 Movement, Makati Business Club, Bayan Muna, Federation of Free Workers, government employees, grupong relihiyoso, at iba pang sektor ang rally para hadlangan ang balak ng mga loyalista ni Estrada na lumusob sa Malaca Atilde;plusmn;ang para patalsikin si Arroyo. (Ulat ni Ellen Fernando)";
//        String article2 = "KASAMA ang mga pangalan nina DILG Undersecretary Rico Puno at retired Chief PNP Jesus Verzosa na tumatanggap ng jueteng payola. Naku ha! Totoo kaya ito? May evidence kaya? Siempre hindi matanggap ng dalawang opisyal ang mga akusasyon sa kanila. Sabi nga, ni Puno alam niyang may mga kalaban siya na sumisira sa kanyang kredibilidad para sirain ng todo-todo ang kanyang pangalan at integridad. Ang problema sa Philippines my Philippines mada ling kumanta kahit sino ay puedeng idawit mapa-bata o matanda, may baktol o wala, bakla o tomboy, pangit o pogi basta target ka isasabit ka. Sabi ng mga asset ng mga kuwago ng ORA MISMO, importante ang may evidence para lalong madiin ang inaakusahan. Ika nga, ganito ang batas sa Philippines my Philippines. Hindi lang sina Verzosa at Puno ang sinisiraan kundi maging si newly appointed CPNP Raul Bacalzo ay inilalaglag din. Ano kaya ang prueba nila lalo na ang nagsasabing sabit si Bacalzo sa jueteng? Hindi ba patay na ang nagsasabit dito? Uso ang siraan blues dahil mga juicy position ang pinaglalabanan kaya naman maraming bumubutas sa mga itinalaga ni P. Noy sa mga tungkulin tulad ng kay Puno at Bacalzo. Bakit? Ewan ko itanong ninyo sa kanila! Gayunman, ayaw naman patulan ni retired Lingayen-Dagupan Archbishop Oscar Cruz na gawing legal ang jueteng. Bakit? Sabi ni Archbishop, na naniniwala siya na mahihirapang gawing legal ang jueteng dahil kapag nangyari ito, ang mangyayari lang ay itutulak naman ang ibang illegal na sugal na gawin legal. Ika nga, magiging tsubibo lang ang isyu. Alvarez, susugpuin ang smuggling HULI DITO, huli doon, kaso dito, kaso doon ang ginagawa ngayon ni Bureau of Customs Commissioner Angelito Alvarez versus mga economic saboteur. Sabi ni Alvarez kailangan ang gobierno ng Philippines my Philippines ang kumita sa pamamagitan ng tamang pagbabayad sa taxes and duties at hindi sa bulsa ng mga kamote dapat mapunta ang pera. Sabi ni Alvarez, paiimbestigahan niya ang smuggling activities ng isang J. Tolentino na nagpupuslit ng mga ukay-ukay, imported tires from Bangkok at mga electronics equipment. Sabi ni Alvarez sa mga kuwago ng ORA MISMO, siya mismo ang kakalkal sa mga kargamento ng isang J. Tolentino. Pabubusisi ni Alvarez ang mga customs official na may mga pending case sa DOJ na sangkot sa iba’t-ibang smuggling activities during the past administration. Ayon sa mga asset ng mga kuwago ng ORA MISMO, ang mga kamoteng may mga kaso ay nailagay pa sa mga juicy position sa bureau. Abangan.";
//        String article3 = "Matapos pumalpak sa pinasok na kasunduan sa Moro Islamic Liberation Front (MILF), tuluyan nang binuwag ni Pangulong Arroyo ang GRP peace panel na pinamumunuan ni (ret.) Gen. Rodolfo Garcia. Ayon kay Press Undersecretary Jesus Dureza, ang pagbuwag sa peace panel ay magiging daan upang magsimula muli ang peace process para sa kapayapaan ng Mindanao. Ngunit agad nilinaw ng Malacanang na hindi nangangahulugan na tinatalikuran na ng gobyerno ang peace negotiations sa MILF. Sinabi ni Dureza na maglalagay ng bagong miyembro ng peace panel si Pangulong Arroyo sa mga susunod na araw upang muling magsimula ang peace process. Kasabay nito ay kinumpirma rin ng Pangulo na hindi na lalagdaan ng gob yerno ang draft ng Memorandum of Agreement on Ancestral Domain (MOA-AD). “In the light of the violent incidents by the MILF, the government will not sign the Memorandum of Agreement on Ancestral Domain. The peace process must proceed only after we have expanded the stakeholders who will join the consultations,” wika ng Pangulo sa kanyang speech sa mga Muslim na dumalo sa TESDA job fair for Muslims sa Philippine Trade Training Center sa Pasay City.; Tiwala naman ang liderato ng MILF na magpapatuloy pa rin ang peace talks sa pagitan ng pamahalaan at ng kanilang panig. Umaasa naman si Cotabato Bishop Jose Colin Bagaforo na sa tamang panahon ay makapagtatalaga ng mga bagong indibdiwal ang pamahalaan na siyang magpapatuloy ng usapang pangkapayapaan sa Mindanao. Bagamat wala naman aniya siyang partikular na tao na mairerekomenda, isinuhestyon nito na maaaring ang asosasyon ng mga gobernador sa Mindanao o sa mga concerned areas, ay mag-appoint o magtalaga sa kanilang hanay ng uupo sa panel.";
//
//        String[] articles = { article1, article2, article3 };

        Stemmer stemmer = new Stemmer(writer, scanner);
        Indexer indexer = new Indexer(writer);

        int articleCounter = 0;
        for(String article : articles) {
            indexer.addArticle(article);
            ArrayList<String> words =  reader.getWords(article);
            int wordCounter = 0;
            for(String word : words) {
                word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase();
                System.out.println(String.format("Stemming word %s (%s) of %s from article %s.", (wordCounter + 1), word, words.size(), articleCounter));
                String stemmedWord = stemmer.stem(word);
                if(stemmedWord != null) {
                    indexer.addWord(stemmedWord, articleCounter);
                }
                wordCounter++;
            }
            articleCounter++;
        }

//        words.addAll(reader.getWords(article1));
//        words.addAll(reader.getWords(article2));
//        words.addAll(reader.getWords(article3));

        stemmer.write();
        indexer.write();
//        Stemmer locationStemmer = new Stemmer("Location", locationRegex, writer, scanner);
//        counter = 0;
//        for(String line : words) {
//            System.out.println("Location - Classifying line " + (counter + 1) + " of " + words.size() + ".");
//            locationStemmer.stem(line);
//            counter++;
//        }
//
//        Stemmer dateStemmer = new Stemmer("Date", dateRegex, writer, scanner);
//        counter = 0;
//        for(String line : words) {
//            System.out.println("Date - Classifying line " + (counter + 1) + " of " + words.size() + ".");
//            dateStemmer.stem(line);
//            counter++;
//        }
//
//
//        stemmer.writeCounter();
//        locationStemmer.writeCounter();
//        dateStemmer.writeCounter();
//
//        stemmer.verification(0.005);
//        locationStemmer.verification(0.005);
//        dateStemmer.verification(0.005);
//
//        stemmer.writeVerificationCounter();
//        locationStemmer.writeVerificationCounter();
//        dateStemmer.writeVerificationCounter();
//
//        stemmer.write();
//        locationStemmer.write();
//        dateStemmer.write();
//
//        stemmer.writeVerification();
//        locationStemmer.writeVerification();
//        dateStemmer.writeVerification();

        searcher = new Searcher(indexer.getWordsMap());

        writer.closeWriters();
        scanner.close();

        System.out.println(indexer.getWordsMap().keySet().size());
    }

    public void search(ArrayList<String> queries) {
        System.out.println("");
        System.out.println(searcher.search(queries));
    }

    public static void main(String[] args) {
        FilipinoStemmer m = new FilipinoStemmer();
        m.init();

        ArrayList<String> queries;

//        queries = new ArrayList<>();
//        queries.add("dalas");
//        queries.add("tungo");
//        m.search(queries);

        queries = new ArrayList<>();
        queries.add("malaking");
        queries.add("tulong");
        queries.add("ng");
        queries.add("pamahalaan");
        m.search(queries);

//        queries = new ArrayList<>();
//        queries.add("darati");
//        queries.add("lagda");
//        queries.add("grupo");
//        m.search(queries);
//
//        queries = new ArrayList<>();
//        queries.add("pamahalaan");
//        m.search(queries);
//
//        queries = new ArrayList<>();
//        queries.add("pamahalaang");
//        m.search(queries);
//
//        queries = new ArrayList<>();
//        queries.add("project");
//        m.search(queries);
//
//        queries = new ArrayList<>();
//        queries.add("projects");
//        m.search(queries);
    }
}
