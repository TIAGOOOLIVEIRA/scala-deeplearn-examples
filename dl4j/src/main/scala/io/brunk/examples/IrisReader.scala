/*
 * Copyright 2017 Sören Brunk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.brunk.examples

import java.io.File

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.nd4j.linalg.dataset.SplitTestAndTrain
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize

object IrisReader {
  val numLinesToSkip = 1

  val batchSize  = 150
  val labelIndex = 4
  val numLabels  = 3

  val seed = 1

  def readData(): SplitTestAndTrain = {
    val recordReader = new CSVRecordReader(numLinesToSkip, ',')
    recordReader.initialize(new FileSplit(new File("data/iris.csv")))
    val iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex, numLabels)
    val dataSet  = iterator.next() // read all data in a single batch
    dataSet.shuffle(seed)
    val testAndTrain = dataSet.splitTestAndTrain(0.67)
    val train        = testAndTrain.getTrain
    val test         = testAndTrain.getTest

//    val normalizer = new NormalizerStandardize
//    normalizer.fit(train)
//    normalizer.transform(train) // normalize training data
//    normalizer.transform(test)  // normalize test data
    testAndTrain
  }
}
