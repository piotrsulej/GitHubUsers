package pl.sulej.utilities.dependency

import dagger.Binds
import dagger.Module
import pl.sulej.utilities.images.GlideImageLoader
import pl.sulej.utilities.images.ImageLoader

@Module
abstract class ImagesModule {

    @Binds
    abstract fun bindImageLoader(loader: GlideImageLoader): ImageLoader
}