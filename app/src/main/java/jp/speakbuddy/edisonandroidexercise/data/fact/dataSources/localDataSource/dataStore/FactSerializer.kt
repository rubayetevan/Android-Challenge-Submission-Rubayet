package jp.speakbuddy.edisonandroidexercise.data.fact.dataSources.localDataSource.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import jp.speakbuddy.edisonandroidexercise.FactProto
import java.io.InputStream
import java.io.OutputStream

object FactSerializer: Serializer<FactProto> {
    override val defaultValue: FactProto = FactProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FactProto {
        try {
            return FactProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: FactProto, output: OutputStream) = t.writeTo(output)
}