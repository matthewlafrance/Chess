MAIN=Main
BUILD_DIR=target
SRC_DIR=src

.PHONY: run

run:
	javac -d $(BUILD_DIR) $(SRC_DIR)/$(MAIN).java
	java --class-path $(BUILD_DIR) $(MAIN)
