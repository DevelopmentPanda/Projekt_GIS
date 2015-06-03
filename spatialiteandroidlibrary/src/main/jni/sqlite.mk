include $(CLEAR_VARS)
# ./configure --build=x86_64-pc-linux-gnu --host=arm-linux-eabi
LOCAL_MODULE    := sqlite

# SQLite flags copied from ASOP
common_sqlite_flags := \
        -DHAVE_USLEEP=1 \
        -DSQLITE_DEFAULT_JOURNAL_SIZE_LIMIT=1048576 \
        -DSQLITE_THREADSAFE=1 \
        -DNDEBUG=1 \
        -DSQLITE_ENABLE_MEMORY_MANAGEMENT=1 \
        -DSQLITE_DEFAULT_AUTOVACUUM=1 \
        -DSQLITE_TEMP_STORE=3 \
        -DSQLITE_ENABLE_FTS3 \
        -DSQLITE_ENABLE_FTS3_BACKWARDS \
        -DSQLITE_DEFAULT_FILE_FORMAT=4 \
        -DSQLITE_ENABLE_RTREE=1 \
        -DSQLITE_OMIT_BUILTIN_TEST=1

LOCAL_CFLAGS    := $(common_sqlite_flags)
        
LOCAL_LDLIBS    := -llog 
LOCAL_C_INCLUDES := 
LOCAL_SRC_FILES := $(SQLITE_PATH)/sqlite3.c
LOCAL_STATIC_LIBRARIES := 

include $(BUILD_STATIC_LIBRARY)