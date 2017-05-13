(ns text-cluster.default-options
  (:require [text-cluster.utils :refer :all])
  (:import [edu.upc.freeling ConfigOptions InvokeOptions AnalysisLevel]))

(def textserver:level->freeling:level
  #(or ({"semgraph" AnalysisLevel/SEMGRAPH
         "coref"    AnalysisLevel/COREF
         "dep"      AnalysisLevel/DEP
         "ident"    AnalysisLevel/IDENT
         "morfo"    AnalysisLevel/MORFO
         "parsed"   AnalysisLevel/PARSED
         "tagged"   AnalysisLevel/TAGGED
         "senses"   AnalysisLevel/SENSES
         "shallow"  AnalysisLevel/SHALLOW} %)
       (throw (Exception. (str "Processing level " % " not recognised.")))))

(defn mk-config-options [freeling-dir-path lang]
  (let [path freeling-dir-path
        lpath (str freeling-dir-path "/" lang "/")]
    (doto (ConfigOptions.)
      ;; Tokenizer configuration file
     (.setTOK_TokenizerFile (str-or-throw lpath "tokenizer.dat"))
     ;; Splitter configuration file
     (.setSPLIT_SplitterFile (str-or-throw lpath "splitter.dat"))
     ;; Morphological analyzer options
     (.setMACO_Decimal ".")
     (.setMACO_Thousand ",")
     (.setMACO_LocutionsFile  (str-or-throw lpath "locucions.dat"))
     (.setMACO_QuantitiesFile  (str-or-throw lpath "quantities.dat"))
     (.setMACO_AffixFile  (str-or-throw lpath  "afixos.dat"))
     (.setMACO_ProbabilityFile  (str-or-throw lpath "probabilitats.dat"))
     (.setMACO_DictionaryFile  (str-or-throw lpath "dicc.src"))
     (.setMACO_NPDataFile  (str-or-throw lpath "np.dat"))
     (.setMACO_PunctuationFile  (str-or-throw path "common/punct.dat"))
     (.setMACO_ProbabilityThreshold  0.001)
     ;; NEC config file
     (.setNEC_NECFile  (str-or-throw lpath  "nerc/nec/nec-ab-poor1.dat"))
     ;; Sense annotator and WSD config files
     (.setSENSE_ConfigFile  (str-or-throw lpath "senses.dat"))
     (.setUKB_ConfigFile  (str-or-throw lpath "ukb.dat"))
     ;; Tagger options
     (.setTAGGER_HMMFile  (str-or-throw lpath "tagger.dat"))
     (.setTAGGER_ForceSelect ForceSelectStrategy/RETOK)
     ;; Chart parser config file
     (.setPARSER_GrammarFile  (str-or-throw lpath "chunker/grammar-chunk.dat"))
     ;; Dependency parsers config files
     (.setDEP_TxalaFile  (str-or-throw lpath "dep_txala/dependences.dat"))
     (.setDEP_TreelerFile  (str-or-throw lpath "dep_treeler/dependences.dat")) ; This was in dep_treeler/labeled/dependences.dat originally.
     ;; Coreference resolution config file
     (.setCOREF_CorefFile  (str-or-throw lpath "coref/relaxcor/relaxcor.dat"))
     ;; Semantic graph config file
     (.setSEMGRAPH_SemGraphFile (str-or-throw lpath "semgraph/semgraph-SRL.dat")))))

(defn mk-invoke-options [level]
  (doto (InvokeOptions.)
    (.setInputLevel AnalysisLevel/TEXT)
    (.setOutputLevel (textserver:level->freeling:level level))
                                        ; Activate/deactivate morphological analyzer modules
    (.setMACO_UserMap  false)
    (.setMACO_AffixAnalysis  true)
    (.setMACO_MultiwordsDetection  true)
    (.setMACO_NumbersDetection  true)
    (.setMACO_PunctuationDetection  true)
    (.setMACO_DatesDetection  true)
    (.setMACO_QuantitiesDetection   true)
    (.setMACO_DictionarySearch  true)
    (.setMACO_ProbabilityAssignment  true)
    (.setMACO_CompoundAnalysis  false)
    (.setMACO_NERecognition  true)
    (.setMACO_RetokContractions  false)
    (.setNEC_NEClassification  true)
    (.setSENSE_WSD_which WSDAlgorithm/UKB)
    (.setTAGGER_which TaggerAlgorithm/HMM)
    (.setDEP_which  DependencyParser/TREELER)))
