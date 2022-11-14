package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository: JpaRepository<Note, Long>