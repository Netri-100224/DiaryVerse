package net.engineeringdigest.journalApp.DTO;


import lombok.Data;
import net.engineeringdigest.journalApp.Entity.JournalEntity;

import java.time.LocalDateTime;

@Data
public class JournalDTO {
        private String id;
        private String title;
        private String content;
        private LocalDateTime date;

        public JournalDTO(JournalEntity journal) {
            this.id = journal.getID().toHexString(); // flatten ObjectId
            this.title = journal.getTitle();
            this.content = journal.getContent();
            this.date = journal.getDate();
        }

        // getters and setters
    }

