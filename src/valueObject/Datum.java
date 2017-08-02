
package valueObject;


public class Datum {

    private String isbn;
    private String updated_at;
    private Object id;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "Datum [isbn=" + isbn + ", updated_at=" + updated_at + ", id=" + id + "]";
	}

}
