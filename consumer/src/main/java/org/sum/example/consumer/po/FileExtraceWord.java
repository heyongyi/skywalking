package org.sum.example.consumer.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FileExtraceWord {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private Long fileId;
	
	@Column(nullable = false, length = 128)
    private String word;
	
	@Column(name = "wordType", nullable = false)
	private Integer wordTypeId;
	
	@Column(nullable = false)
	private Integer count;
	@Getter
    @AllArgsConstructor
    public enum WordType {
        SENSITIVE(1), HOTWORD(2), PHONE(101), EMAIL(102), ORGANIZATION(103), PERSON(104), IDCARD(105), GPE(106);

        private int value;

        public static WordType parse(int id) {
            for (WordType item : WordType.values()) {
                if (item.getValue() == id) {
                    return item;
                }
            }
            return null;
        }
        
    }
	public static List<String> wordTypeList=Arrays.stream(WordType.values()).map(t->t.toString()).collect(Collectors.toList());
    public WordType getWordType() {
        return WordType.parse(this.wordTypeId);
    }

    public void setWordType(WordType wordType) {
        this.wordTypeId = wordType.getValue();
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileExtraceWord other = (FileExtraceWord) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		if (wordTypeId == null) {
			if (other.wordTypeId != null)
				return false;
		} else if (!wordTypeId.equals(other.wordTypeId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		result = prime * result
				+ ((wordTypeId == null) ? 0 : wordTypeId.hashCode());
		return result;
	}
    
}
