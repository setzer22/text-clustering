export FREELING_HOME=/usr/share/freeling/
export FREELING_LIB=/usr/lib/

export LD_LIBRARY_PATH="${LD_LIBRARY_PATH:+$LD_LIBRARY_PATH:}$FREELING_LIB:$PWD/native/"

lein repl
