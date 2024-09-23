import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicate {
    public static void main(String[] args) {
        List<Staff> staffList1 =
                new ArrayList<>(
                        Arrays.asList(
                                new Staff("1", "nakamoto"),
                                new Staff("2", "takagi"),
                                new Staff("3", "katoh"),
                                new Staff("4", "shimura")));
        List<Staff> staffList2 =
                new ArrayList<>(Arrays.asList(new Staff("1", "AAA"), new Staff("2", "BBB")));
        System.out.println(staffList1);
        System.out.println(staffList2);
        List<String> idList = staffList2.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<Staff> result =
                staffList1.stream()
                        .filter(x -> !idList.contains(x.getId()))
                        .collect(Collectors.toList());
        System.out.println(result);
    }

    public static class Staff {
        private String id;
        private String name;

        public Staff(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Staff{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
        }
    }
}

/* 【実行結果】
 * [Staff{id='1', name='nakamoto'}, Staff{id='2', name='takagi'}, Staff{id='3', name='katoh'}, Staff{id='4', name='shimura'}]
 * [Staff{id='1', name='AAA'}, Staff{id='2', name='BBB'}]
 * [Staff{id='3', name='katoh'}, Staff{id='4', name='shimura'}]
 */
