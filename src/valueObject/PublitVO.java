
package valueObject;

import java.util.List;

public class PublitVO {

    private Integer count;
    private List<Datum> data = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "PublitVO [count=" + count + ", data=" + data + "]";
	}

}
