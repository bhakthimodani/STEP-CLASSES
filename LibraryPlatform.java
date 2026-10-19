
interface Reservable {
    void reserve(String member);
}

interface Downloadable {
    void download();
}

abstract class Resource {
    String id;

    Resource(String id) {
        this.id = id;
    }
}

class Book extends Resource implements Reservable {
    Book(String id) {
        super(id);
    }

    public void reserve(String member) {
        System.out.println(id + " reserved for " + member);
    }
}

class EBook extends Resource
        implements Reservable, Downloadable {
    EBook(String id) {
        super(id);
    }

    public void reserve(String member) {
        System.out.println(id + " reserved for " + member);
    }

    public void download() {
        System.out.println(id + " downloaded");
    }
}

public class LibraryPlatform {
    static Resource[] resources = new Resource[100];
    static int count = 0;

    static void add(Resource resource) {
        int i = 0;

        while (i < count &&
               resources[i].id.compareTo(resource.id) < 0) {
            i++;
        }

        if (i < count &&
            resources[i].id.equals(resource.id)) {
            System.out.println("duplicate rejected");
            return;
        }

        for (int j = count; j > i; j--) {
            resources[j] = resources[j - 1];
        }

        resources[i] = resource;
        count++;
        System.out.println(resource.id + " added");
    }

    static int find(String id) {
        int low = 0, high = count - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = resources[mid].id.compareTo(id);

            if (cmp == 0) return mid;
            if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        add(new Book("B1"));
        add(new Book("B1"));
        add(new EBook("E1"));

        Resource r = resources[find("B1")];

        if (r instanceof Reservable) {
            ((Reservable) r).reserve("M1");
        }

        if (r instanceof Downloadable) {
            ((Downloadable) r).download();
        } else {
            System.out.println(
                r.id + " rejected: download unsupported"
            );
        }

        Resource e = resources[find("E1")];

        if (e instanceof Downloadable) {
            ((Downloadable) e).download();
        }

        System.out.println(
            "E1 found at index " + find("E1")
        );
    }
}
